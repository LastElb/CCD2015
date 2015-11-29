(function(){
    var canvas = document.getElementById('hexmap');

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

    if (canvas.getContext){
        var ctx = canvas.getContext('2d');

        ctx.fillStyle = "#000000";
        ctx.strokeStyle = "#CCCCCC";
        ctx.lineWidth = 0;

        drawBoard(ctx, boardWidth, boardHeight);

        canvas.addEventListener("mousemove", function(eventInfo) {
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

            drawBoard(ctx, boardWidth, boardHeight);

            // Check if the mouse's coords are on the board
            if(hexX >= 0 && hexX < boardWidth) {
                if(hexY >= 0 && hexY < boardHeight) {
                  if (isHexagonValid(hexX, hexY)) {
                    // ctx.fillStyle = "#000000";
                    // drawHexagon(ctx, screenX, screenY, true);
                  }
                }
            }
        });
    }

    function drawBoard(canvasContext, width, height) {
        var i,
            j;
        var colors = ["#DEFFD6", "#4AA631", "#8CDB73"]

        for(i = 0; i < width; ++i) {
            for(j = 0; j < height; ++j) {
              if (isHexagonValid(i,j)) {
                if (j%2 == 0) {
                  ctx.fillStyle = colors[i%3];
                }
                if (j%2 == 1) {
                  ctx.fillStyle = colors[(i+2)%3];
                }
                // if (j == 6) {
                //   ctx.fillStyle = colors[(i+0)%3];
                // }
                drawHexagon(
                    ctx,
                    i * hexRectangleWidth + ((j % 2) * hexRadius),
                    j * (sideLength + hexHeight),
                    true
                );
              }

            }
        }
    }

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

    function drawHexagon(canvasContext, x, y, fill) {
        var fill = fill || false;

        canvasContext.beginPath();
        canvasContext.moveTo(x + hexRadius, y);
        canvasContext.lineTo(x + hexRectangleWidth, y + hexHeight);
        canvasContext.lineTo(x + hexRectangleWidth, y + hexHeight + sideLength);
        canvasContext.lineTo(x + hexRadius, y + hexRectangleHeight);
        canvasContext.lineTo(x, y + sideLength + hexHeight);
        canvasContext.lineTo(x, y + hexHeight);
        canvasContext.closePath();

        if(fill) {
            canvasContext.fill();
        }
        // canvasContext.stroke();
    }

})();
