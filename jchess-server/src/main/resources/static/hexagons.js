angular.module('jchess', [])
    .filter('reverse', function() {
        return function(items) {
            if (items && items.slice())
                return items.slice().reverse();
        };
    })
    .factory('WebsocketService', ['$q', '$timeout', function($q, $timeout) {
        var service = {},
            listener = $q.defer(),
            socket = {
                client: null,
                stomp: null
            };

        service.RECONNECT_TIMEOUT = 30000;
        service.SOCKET_URL = "/stomp";

        service.receive = function() {
            return listener.promise;
        };

        service.subscribeGame = function(gameid, callback) {
            socket.client = new SockJS(service.SOCKET_URL);
            socket.stomp = Stomp.over(socket.client);
            socket.stomp.debug = null;
            socket.stomp.onclose = reconnect;
            socket.stomp.connect({}, function () {
                socket.stomp.subscribe('/game/'+gameid, function(data) {
                    listener.notify(data);
                });
                if (callback) callback();
            });
        };

        service.subscribePlayer = function(gameid, playerid, callback) {
            socket.client = new SockJS(service.SOCKET_URL);
            socket.stomp = Stomp.over(socket.client);
            socket.stomp.debug = null;
            socket.stomp.onclose = reconnect;
            socket.stomp.connect({}, function () {
                socket.stomp.subscribe('/game/'+gameid + '/' + playerid, function(data) {
                    console.log(data);
                    listener.notify(data);
                });
                if (callback) callback();
            });
        };

        var reconnect = function() {
            $timeout(function() {
                initialize();
            }, this.RECONNECT_TIMEOUT);
        };

        return service;
    }])
    .controller('chessboardController', function($scope, WebsocketService, $http, $timeout) {

        $scope.editClient = {};
        $scope.players = [];
        $scope.activeClient = null;
        $scope.selectedField = null;
        $scope.possibleMoves = [];
        $scope.requestInQueue = false;

        $scope.performMovement = function(targetField) {
            $http.get(($scope.host ? $scope.host : '') + '/game/' + $scope.activeClient.connectedGameId + '/performMoveByField/'
                + $scope.selectedField + '/' + $scope.activeClient.id + '/' + targetField)
                .success(function(message) {
                    $scope.possibleMoves = [];
                    $scope.updateGame($scope.activeClient.connectedGameId);
                })
                .error(function(message) {
                    console.log(message);
                    notie.alert(3, message.message || message, 5);
                })
        };

        $scope.updateSelectedField = function(field) {
            var select = false;
            if (field == $scope.selectedField) {
                $scope.selectedField = null;
                $scope.possibleMoves = [];
            } else if ($scope.selectedField == null) {
                $scope.selectedField = field;
                if ($scope.showPossibleMoves)
                    $scope.updatePossibleMoves();
                select = true;
            } else {
                $scope.performMovement(field);
                $scope.selectedField = null;
            }
            $scope.$apply();
            return select;
        };

        $scope.updatePossibleMoves = function () {
            $http.get(($scope.host ? $scope.host : '') + '/game/' + $scope.activeClient.connectedGameId +
                '/possibleMovesByField/' + $scope.selectedField )
                .success(function(response) {
                    console.log(response);
                    $scope.possibleMoves = response;
                    drawBoard(ctx, boardWidth, boardHeight);
                })
                .error(function(response) {
                    notie.alert(3, response.message || response, 5);
                })
        };

        $scope.updateGame = function(gameid) {
            $http.get(($scope.host ? $scope.host : '') + '/game/' + gameid + "/full")
                .success(function(message) {
                    $scope.game = message;
                    drawBoard(ctx, boardWidth, boardHeight);
                })
                .error(function(message) {
                    notie.alert(3, message.message || message, 5);
                })
        };

        $scope.addClientPlayer = function() {
            $http.post(($scope.host ? $scope.host : '') + '/host/joinAsPlayer/' + $scope.gameid, $scope.editClient)
                .success(function(message) {
                    console.log(message);
                    $scope.players.push(message);
                    WebsocketService.subscribePlayer($scope.gameid, message.id, null);
                    notie.alert(1, 'Spieler erfolgreich beigetreten', 2);
                })
                .error(function(message) {
                    console.log(message);
                    notie.alert(3, message.message || message, 5);
                });
        };

        $scope.requestGame = function() {
            $http.get(($scope.host ? $scope.host : '') + '/host/byGameMode/' + $scope.gamemode)
                .success(function(response) {
                    $scope.requestedGame = response;
                })
                .error(function(response) {
                    console.log(response);
                    notie.alert(3, response.message || response, 5);
                })
        };

        WebsocketService.receive().then(null, null, function(message) {
            // Get channel
            var count = message.headers.destination.split('/');
            if (count.length == 4) {
                // Player message
                var playerid = count[3];
                var messageProcessed = false;
                angular.forEach($scope.players, function(player) {
                    if (player.id == playerid && JSON.parse(message.body).itYouTurn == true) {
                        $scope.activeClient = player;
                        $scope.updateGame(count[2]);
                        $scope.showAddPlayer = false;
                        $scope.showCreateGame = false;
                        messageProcessed = true;
                    }
                });
                if (!messageProcessed) {
                    if (JSON.parse(message.body).itYouTurn == false) {
                        $scope.activeClient = JSON.parse(message.body);
                        requestLimiter();
                    }
                    if (JSON.parse(message.body).name) {
                        notie.alert(2, 'Player ' + JSON.parse(message.body).name + ' is defeated!', 5);
                    }
                }
            }
        });

        function requestLimiter() {
            if ($scope.requestInQueue)
                return;
            $scope.requestInQueue = true;
            $timeout(function() {
                if ($scope.requestInQueue) {
                    $scope.requestInQueue = false;
                    $scope.updateGame($scope.players[0].connectedGameId);
                }
            }, 1000);
        }


        var canvas = document.getElementById('hexmap');
        var ctx;
        var hexHeight,
            hexRadius,
            hexRectangleHeight,
            hexRectangleWidth,
            hexagonAngle = 0.523598776, // 30 degrees in radians
            sideLength = 28,
            boardWidth = 13,
            boardHeight = 13;

        hexHeight = Math.sin(hexagonAngle) * sideLength;
        hexRadius = Math.cos(hexagonAngle) * sideLength;
        hexRectangleHeight = sideLength + 2 * hexHeight;
        hexRectangleWidth = 2 * hexRadius;

        if (canvas.getContext) {
            ctx = canvas.getContext('2d');
            ctx.fillStyle = "#000000";
            ctx.strokeStyle = "#CCCCCC";
            ctx.lineWidth = 0;

            drawBoard(ctx, boardWidth, boardHeight, false);

            canvas.addEventListener("click", function(eventInfo) {
                var x,
                    y,
                    hexX,
                    hexY,
                    screenX,
                    screenY,
                    rect;

                rect = canvas.getBoundingClientRect();

                x = eventInfo.clientX - rect.left;
                y = eventInfo.clientY - rect.top;

                hexY = Math.floor(y / (hexHeight + sideLength));
                // console.log("hexY: " + hexY);
                hexX = Math.floor((x - (hexY % 2) * hexRadius) / hexRectangleWidth);
                // console.log("hexX: " + hexX);
                screenX = hexX * hexRectangleWidth + ((hexY % 2) * hexRadius);
                screenY = hexY * (hexHeight + sideLength);

                ctx.clearRect(0, 0, canvas.width, canvas.height);

                // Check if the mouse's coords are on the board
                if(hexX >= 0 && hexX < boardWidth && hexY >= 0 && hexY < boardHeight) {
                    var notation = getHexagonNotation(hexX, hexY);
                    if ($scope.game && isHexagonValid(hexX, hexY)) {
                        $scope.updateSelectedField(notation);
                    }
                }

                drawBoard(ctx, boardWidth, boardHeight);
            });

            canvas.addEventListener("mousemove", function(eventInfo) {
                var x,
                    y,
                    hexX,
                    hexY,
                    rect;

                rect = canvas.getBoundingClientRect();

                x = eventInfo.clientX - rect.left;
                y = eventInfo.clientY - rect.top;

                hexY = Math.floor(y / (hexHeight + sideLength));
                hexX = Math.floor((x - (hexY % 2) * hexRadius) / hexRectangleWidth);

                // Check if the mouse's coords are on the board
                if(hexX >= 0 && hexX < boardWidth && hexY >= 0 && hexY < boardHeight) {
                    $scope.hoveredField = getHexagonNotation(hexX, hexY);
                    $scope.$apply();
                }
            });
        }

        /**
         * Draw the whole chessboard and all figures.
         * @param canvasContext The canvas to paint on
         * @param width The amount of hexagons in horizontal view
         * @param height The amount of hexagons in vertical view
         */
        function drawBoard(canvasContext, width, height) {
            var i,
                j;
            // The field colors
            var colors = ["#C7B299", "#8CC63F", "#006837"];

            for(i = 0; i < width; ++i) {
                for(j = 0; j < height; ++j) {
                    if (isHexagonValid(i,j)) {
                        if (j%2 == 0) {
                            canvasContext.fillStyle = colors[i%3];
                        }
                        if (j%2 == 1) {
                            canvasContext.fillStyle = colors[(i+2)%3];
                        }
                        var figureDrawing = drawingFigure(getHexagonNotation(i, j));
                        var notation = getHexagonNotation(i, j);
                        if ($scope.showPossibleMoves && $scope.possibleMoves && $scope.possibleMoves.length > 1) {
                            angular.forEach($scope.possibleMoves, function(field) {
                                if (field.notation == notation)
                                    //canvasContext.fillStyle = "#FF6A00";
                                    canvasContext.fillStyle = tinycolor(canvasContext.fillStyle).greyscale().toHexString();
                            });
                        }
                        if ($scope.selectedField && notation == $scope.selectedField) {
                            canvasContext.fillStyle = "red";
                        }
                        drawHexagon(
                            canvasContext,
                            i * hexRectangleWidth + ((j % 2) * hexRadius),
                            j * (sideLength + hexHeight),
                            figureDrawing.figure,
                            figureDrawing.color
                        );
                    }
                }
            }
        }

        $scope.drawingFigure = function(figureId) {
            var figure = null;
            var figureColor = null;
            if ($scope.game) {
                // Get notation of current field
                angular.forEach($scope.game.chessboard.figures, function(figureObject) {
                    if (figureObject.id == figureId) {
                        var picture = figureObject.pictureId.split("-");
                        figureColor = picture[1];
                        switch (picture[0]) {
                            case "rook":
                                figure = "♖";
                                break;
                            case "knight":
                                figure = "♞";
                                break;
                            case "bishop":
                                figure = "♝";
                                break;
                            case "queen":
                                figure = "♛";
                                break;
                            case "king":
                                figure = "♚";
                                break;
                            case "pawn":
                                figure = "♟";
                                break;
                        }
                    }
                })
            }
            return {figure:figure, color:figureColor};
        };

        function drawingFigure(notation) {
            var figure = null;
            var figureColor = null;
            if ($scope.game) {
                // Get notation of current field
                angular.forEach($scope.game.chessboard.figures, function(figureObject) {
                    // Only active figures
                    if (!figureObject.removed && figureObject.position.notation == notation) {
                        var picture = figureObject.pictureId.split("-");
                        figureColor = picture[1];
                        switch (picture[0]) {
                            case "rook":
                                figure = "♖";
                                break;
                            case "knight":
                                figure = "♞";
                                break;
                            case "bishop":
                                figure = "♝";
                                break;
                            case "queen":
                                figure = "♛";
                                break;
                            case "king":
                                figure = "♚";
                                break;
                            case "pawn":
                                figure = "♟";
                                break;
                        }
                    }
                })
            }
            return {figure:figure, color:figureColor};
        }

        /**
         * Returns if the hexagons position is valid and should be drawn.
         * @param i The column
         * @param j The row
         * @returns {boolean}
         */
        function isHexagonValid(i, j) {
            var render = true;
            // Row A
            if (j == 0 && i < 3 || j == 0 && i > 10)
                render = false;
            // Row B
            if (j == 1 && i < 2 || j == 1 && i > 10)
                render = false;
            // Row C
            if (j == 2 && i < 2 || j == 2 && i > 11)
                render = false;
            // Row D
            if (j == 3 && i < 1 || j == 3 && i > 11)
                render = false;
            // Row E
            if (j == 4 && i < 1 || j == 4 && i > 12)
                render = false;
            // Row F - no logic
            // Row G
            if (j == 6 && i < 1 || j == 6 && i > 12)
                render = false;
            // Row H
            if (j == 7 && i < 1 || j == 7 && i > 11)
                render = false;
            // Row I
            if (j == 8 && i < 2 || j == 8 && i > 11)
                render = false;
            // Row J
            if (j == 9 && i < 2 || j == 9 && i > 10)
                render = false;
            // Row K
            if (j == 10 && i < 3 || j == 10 && i > 10)
                render = false;
            // Row L
            if (j == 11 && i < 3 || j == 11 && i > 9)
                render = false;
            // Row M
            if (j == 12 && i < 4 || j == 12 && i > 9)
                render = false;
            return render;
        }

        /**
         * Get the notation of a field on the canvas based on the hexagon position
         * @param i The column
         * @param j The row
         * @returns {string}
         */
        function getHexagonNotation(i, j) {
            var letter, number;
            // Row A
            if (j == 0 && i >= 3 || j == 0 && i <= 10) {
                letter = "a";
                number = i - 2;
            }
            // Row B
            if (j == 1 && i >= 2 || j == 1 && i <= 10) {
                letter = "b";
                number = i - 1;
            }
            // Row C
            if (j == 2 && i >= 2 || j == 2 && i <= 11) {
                letter = "c";
                number = i - 1;
            }
            // Row D
            if (j == 3 && i >= 1 || j == 3 && i <= 11) {
                letter = "d";
                number = i;
            }
            // Row E
            if (j == 4 && i >= 1 || j == 4 && i <= 12) {
                letter = "e";
                number = i;
            }
            // Row F
            if (j == 5 && i >= 1 || j == 5 && i <= 12) {
                letter = "f";
                number = i + 1;
            }
            // Row G
            if (j == 6 && i >= 1 || j == 6 && i <= 12) {
                letter = "g";
                number = i + 1;
            }
            // Row H
            if (j == 7 && i >= 1 || j == 7 && i <= 11) {
                letter = "h";
                number = i + 2;
            }
            // Row I
            if (j == 8 && i >= 2 || j == 8 && i <= 11) {
                letter = "i";
                number = i + 2;
            }
            // Row J
            if (j == 9 && i >= 2 || j == 9 && i <= 10) {
                letter = "j";
                number = i + 3;
            }
            // Row K
            if (j == 10 && i >= 3 || j == 10 && i <= 10) {
                letter = "k";
                number = i + 3;
            }
            // Row L
            if (j == 11 && i >= 3 || j == 11 && i <= 9) {
                letter = "l";
                number = i + 4;
            }
            // Row M
            if (j == 12 && i >= 4 || j == 12 && i <= 9) {
                letter = "m";
                number = i + 4;
            }
            return letter + number.toString();
        }

        /**
         * Draws a single hexagon and the figure on it (if there is a figure on this position
         * @param canvasContext The canvas to paint on
         * @param x Pixel on the canvas
         * @param y Pixel on the canvas
         * @param figure The figure to paint on the field. Can be null to not draw any figure.
         * @param figureColor The color of the figure
         */
        function drawHexagon(canvasContext, x, y, figure, figureColor) {
            canvasContext.beginPath();
            canvasContext.moveTo(x + hexRadius, y);
            canvasContext.lineTo(x + hexRectangleWidth, y + hexHeight);
            canvasContext.lineTo(x + hexRectangleWidth, y + hexHeight + sideLength);
            canvasContext.lineTo(x + hexRadius, y + hexRectangleHeight);
            canvasContext.lineTo(x, y + sideLength + hexHeight);
            canvasContext.lineTo(x, y + hexHeight);
            canvasContext.closePath();
            canvasContext.fill();

            canvasContext.font="35px sans-serif";
            canvasContext.fillStyle=figureColor;
            if (figure)
                canvasContext.fillText(figure, x + (hexRadius/3.5), y + hexHeight + sideLength);
        }
    });