# JChess Server
This module implements a stand alone server component for the refactoring of JChess.
This work needs to be done for the winters term lecture "Clean Code Development"

The JChess Server module is based on Spring Boot.

## Networking Information
The assumed base URL is ```http://localhost:8080/```. All answers from the server are JSON formatted.

### 1. Game Modes
The first step to do is ask the server for the available game modes by calling
```GET gamemodes/available```.
A possible answer:
```
[
    "default-2-person-chess",
    "default-3-person-chess"
]
```
All game modes listed in the response can be hosted on the server.

### 2. Request a hosted game
After choosing the wanted game mode call ```GET host/byGameMode/default-2-person-chess```. The response contains more detailed information to join the server:
```
{
    "id": "82cqu8tj72zsyx61zh79",
    "gameMode": "default-2-person-chess",
    "maximumPlayers": 2,
    "chessboard": null
}
```

### 3. Join a hosted game
With the ID delivered from the last response players and observers are able to join the game.
```
POST host/joinAsPlayer/82cqu8tj72zsyx61zh79
DATA:
{
  "nickname":"Your nickname"
}
```
The responds with a unique ID for each joined client:
```
{
    "id": "806mwc8x24ng5op3l5aw",
    "nickname": "Your nickname",
    "connectedGameId": "82cqu8tj72zsyx61zh79"
}
```
As soon as enough players joined the game, the game will setup the chessboard and notify the clients via websocket.
It is recommended to subscribe to a websocket with the game id __before__ joining the game.
#### Exceptions
If the ID of the hosted game is wrong, the server responds with the status code ```500```:
```
{
    "timestamp": 1447451561256,
    "status": 500,
    "error": "Internal Server Error",
    "exception": "de.mki.jchess.server.exception.HostedGameNotFoundException",
    "message": "Game with ID '82cqu8tj72zyx61zh79'not found.",
    "path": "/host/joinAsPlayer"
}
```
If there already joined enough players (no observer limit) the server responds with the status code ````500``` as well:
```
{
    "timestamp": 1447451653607,
    "status": 500,
    "error": "Internal Server Error",
    "exception": "de.mki.jchess.server.exception.TooManyPlayersException",
    "message": "There are already enough players registered for the game. Maybe join as observer?",
    "path": "/host/joinAsPlayer"
}
```

### 4. Full game information
To get all available game information request ```game/{game id}/full```
```
{
    "id": "l1wkle738237t6cyccfg",
    "gameMode": "default-2-person-chess",
    "maximumPlayers": 2,
    "chessboard": {
        "figures": [
            {
                "id": "yripqepbztt6nv5d8t2m",
                "position": {
                    "notation": "e1"
                },
                "name": "King",
                "pictureId": "king-white",
                "removed": false
            },
            {
                "id": "e9dnuy3sv86ope3tco4v",
                "position": {
                    "notation": "d1"
                },
                "name": "Queen",
                "pictureId": "queen-white",
                "removed": false
            },
            {
                "id": "wzifa5f8rbrgy1mytrbj",
                "position": {
                    "notation": "a1"
                },
                "name": "Rook",
                "pictureId": "rook-white",
                "removed": false
            },
            {
                "id": "jxrkmnhoxlqffmf88nm4",
                "position": {
                    "notation": "h1"
                },
                "name": "Rook",
                "pictureId": "rook-white",
                "removed": false
            },
            {
                "id": "ff9cmx02atfj4x8xgcva",
                "position": {
                    "notation": "b1"
                },
                "name": "Knight",
                "pictureId": "knight-white",
                "removed": false
            },
            {
                "id": "7a2eydrxgj51urw3kwzv",
                "position": {
                    "notation": "g1"
                },
                "name": "Knight",
                "pictureId": "knight-white",
                "removed": false
            },
            {
                "id": "div39r9vo1qqnxzv55ce",
                "position": {
                    "notation": "f1"
                },
                "name": "Bishop",
                "pictureId": "bishop-white",
                "removed": false
            },
            {
                "id": "co90x9vh0ik1bn69p16o",
                "position": {
                    "notation": "c1"
                },
                "name": "Bishop",
                "pictureId": "bishop-white",
                "removed": false
            },
            {
                "id": "9skobc91vt380gl7bzs2",
                "position": {
                    "notation": "a2"
                },
                "name": "Pawn",
                "pictureId": "pawn-white",
                "removed": false
            },
            {
                "id": "1qyz6p7e4px0uma29kzm",
                "position": {
                    "notation": "b2"
                },
                "name": "Pawn",
                "pictureId": "pawn-white",
                "removed": false
            },
            {
                "id": "gpxyhzq7byba1f0kflvg",
                "position": {
                    "notation": "c2"
                },
                "name": "Pawn",
                "pictureId": "pawn-white",
                "removed": false
            },
            {
                "id": "jf04wt6u4np955pdr690",
                "position": {
                    "notation": "d2"
                },
                "name": "Pawn",
                "pictureId": "pawn-white",
                "removed": false
            },
            {
                "id": "roehqjotvty6kjz6ks3j",
                "position": {
                    "notation": "e2"
                },
                "name": "Pawn",
                "pictureId": "pawn-white",
                "removed": false
            },
            {
                "id": "6fzref0frxtkr4mkil1u",
                "position": {
                    "notation": "f2"
                },
                "name": "Pawn",
                "pictureId": "pawn-white",
                "removed": false
            },
            {
                "id": "07qjwz8v0s95iex1g36i",
                "position": {
                    "notation": "g2"
                },
                "name": "Pawn",
                "pictureId": "pawn-white",
                "removed": false
            },
            {
                "id": "n5padazn5n85px6d04qu",
                "position": {
                    "notation": "h2"
                },
                "name": "Pawn",
                "pictureId": "pawn-white",
                "removed": false
            },
            {
                "id": "72teg0bv265r1bdbpo0z",
                "position": {
                    "notation": "e8"
                },
                "name": "King",
                "pictureId": "king-black",
                "removed": false
            },
            {
                "id": "yg0d83c6sa68r20aqj1o",
                "position": {
                    "notation": "d8"
                },
                "name": "Queen",
                "pictureId": "queen-black",
                "removed": false
            },
            {
                "id": "1k91503xw13n3wik1ila",
                "position": {
                    "notation": "a8"
                },
                "name": "Rook",
                "pictureId": "rook-black",
                "removed": false
            },
            {
                "id": "aeavq9qy0qpzakji1ekc",
                "position": {
                    "notation": "h8"
                },
                "name": "Rook",
                "pictureId": "rook-black",
                "removed": false
            },
            {
                "id": "f3e9crssn4pa6os02vvr",
                "position": {
                    "notation": "b8"
                },
                "name": "Knight",
                "pictureId": "knight-black",
                "removed": false
            },
            {
                "id": "49i2bv36r34q0yhs8bar",
                "position": {
                    "notation": "g8"
                },
                "name": "Knight",
                "pictureId": "knight-black",
                "removed": false
            },
            {
                "id": "o8kid4shw22qjb2jlwfy",
                "position": {
                    "notation": "f8"
                },
                "name": "Bishop",
                "pictureId": "bishop-black",
                "removed": false
            },
            {
                "id": "1ybymr74yn7rkclbod0x",
                "position": {
                    "notation": "c8"
                },
                "name": "Bishop",
                "pictureId": "bishop-black",
                "removed": false
            },
            {
                "id": "t3xkfovcvvm63naicf2l",
                "position": {
                    "notation": "a7"
                },
                "name": "Pawn",
                "pictureId": "pawn-black",
                "removed": false
            },
            {
                "id": "4e3qrs32g1kcgx1mdbto",
                "position": {
                    "notation": "b7"
                },
                "name": "Pawn",
                "pictureId": "pawn-black",
                "removed": false
            },
            {
                "id": "4ai865fbxl7dzxf476jy",
                "position": {
                    "notation": "c7"
                },
                "name": "Pawn",
                "pictureId": "pawn-black",
                "removed": false
            },
            {
                "id": "39j3bcfegpuz1nbdzm9t",
                "position": {
                    "notation": "d7"
                },
                "name": "Pawn",
                "pictureId": "pawn-black",
                "removed": false
            },
            {
                "id": "y8gqfyzux46tplxbvent",
                "position": {
                    "notation": "e7"
                },
                "name": "Pawn",
                "pictureId": "pawn-black",
                "removed": false
            },
            {
                "id": "igix6qk5wxp6vfqbdev2",
                "position": {
                    "notation": "f7"
                },
                "name": "Pawn",
                "pictureId": "pawn-black",
                "removed": false
            },
            {
                "id": "wbt8wpjl1u2hmhtrghcr",
                "position": {
                    "notation": "g7"
                },
                "name": "Pawn",
                "pictureId": "pawn-black",
                "removed": false
            },
            {
                "id": "sebq7wr8f5b5mm7lbrz8",
                "position": {
                    "notation": "h7"
                },
                "name": "Pawn",
                "pictureId": "pawn-black",
                "removed": false
            }
        ],
        "fields": [
            {
                "notation": "a1"
            },
            {
                "notation": "b1"
            },
            {
                "notation": "c1"
            },
            {
                "notation": "d1"
            },
            {
                "notation": "e1"
            },
            {
                "notation": "f1"
            },
            {
                "notation": "g1"
            },
            {
                "notation": "h1"
            },
            {
                "notation": "a2"
            },
            {
                "notation": "b2"
            },
            {
                "notation": "c2"
            },
            {
                "notation": "d2"
            },
            {
                "notation": "e2"
            },
            {
                "notation": "f2"
            },
            {
                "notation": "g2"
            },
            {
                "notation": "h2"
            },
            {
                "notation": "a3"
            },
            {
                "notation": "b3"
            },
            {
                "notation": "c3"
            },
            {
                "notation": "d3"
            },
            {
                "notation": "e3"
            },
            {
                "notation": "f3"
            },
            {
                "notation": "g3"
            },
            {
                "notation": "h3"
            },
            {
                "notation": "a4"
            },
            {
                "notation": "b4"
            },
            {
                "notation": "c4"
            },
            {
                "notation": "d4"
            },
            {
                "notation": "e4"
            },
            {
                "notation": "f4"
            },
            {
                "notation": "g4"
            },
            {
                "notation": "h4"
            },
            {
                "notation": "a5"
            },
            {
                "notation": "b5"
            },
            {
                "notation": "c5"
            },
            {
                "notation": "d5"
            },
            {
                "notation": "e5"
            },
            {
                "notation": "f5"
            },
            {
                "notation": "g5"
            },
            {
                "notation": "h5"
            },
            {
                "notation": "a6"
            },
            {
                "notation": "b6"
            },
            {
                "notation": "c6"
            },
            {
                "notation": "d6"
            },
            {
                "notation": "e6"
            },
            {
                "notation": "f6"
            },
            {
                "notation": "g6"
            },
            {
                "notation": "h6"
            },
            {
                "notation": "a7"
            },
            {
                "notation": "b7"
            },
            {
                "notation": "c7"
            },
            {
                "notation": "d7"
            },
            {
                "notation": "e7"
            },
            {
                "notation": "f7"
            },
            {
                "notation": "g7"
            },
            {
                "notation": "h7"
            },
            {
                "notation": "a8"
            },
            {
                "notation": "b8"
            },
            {
                "notation": "c8"
            },
            {
                "notation": "d8"
            },
            {
                "notation": "e8"
            },
            {
                "notation": "f8"
            },
            {
                "notation": "g8"
            },
            {
                "notation": "h8"
            }
        ],
        "currentPlayer": {
            "nickname": "Your nickname"
        }
    }
}
```
All these information are generated after all players joined the game.
