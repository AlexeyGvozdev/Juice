// const app = require('http').createServer(handler)
// const io = require('socket.io')(app);
const fs = require('fs')
const WebSocket = require('ws');
const wsServer = new WebSocket.Server({port: 8003});
// app.listen(8003)


function onConnect(wsClient) {
  console.log('Новый пользователь');
  // отправка приветственного сообщения клиенту
  wsClient.send('Привет');
  wsClient.on('message', function(message) {
    /* обработчик сообщений от клиента */
  })
  wsClient.on("addTask", () => {
    
    console.log('addTask');
  })
  wsClient.on('close', function() {
    // отправка уведомления в консоль
    console.log('Пользователь отключился');
  })
}

wsServer.on('connection', onConnect);

// function handler (req, res) {
//   fs.readFile(__dirname + '/index.html',
//   function (err, data) {
//     if (err) {
//       res.writeHead(500);
//       return res.end('Error loading index.html');
//     }

//     res.writeHead(200);
//     res.end(data);
//   });
// }

// const initMessage = (socket) => {
//   console.log("12345");
//   socket.emit("connection", "{}")
//   socket.emit("connection", "{}")
//   for (let i = 1; i < 2; i++) {
//     setTimeout(() => {
//       let pseudoId = new Date().getMilliseconds()
//       let answer = JSON.stringify({
//         id: pseudoId, 
//         text: "number " + i,
//         checked: false
//       })
//       socket.emit("newTask", answer)
//       console.log(answer)
//     }, 1000 * i);
//   }
  
//   socket.on("addTask", (req) => {
//     console.log("addtask")
//     socket.emit("newTask", JSON.stringify({
//       id: new Date().getMilliseconds(), 
//       text: req,
//       checked: false
//     }))
//   })
//   socket.on("disconect", async (req) => {
//     console.log(req)
//   })
//   socket.on('hello', async (req) => {
//     console.log(req.data)
//   })
// }

// class Task {
//   constructor(id, text, name) {

//   }
// }

// io.on('connection', initMessage)
// // io.on('connection', function (socket) {
// //   initMessage(socket)
// //   console.log(8989)
// //   socket.emit('news', { hello: 'world' });
// //   socket.on('my other event', function (data) {
// //     console.log(data);
// //   });
// // });

// // io.on("hello", (req, res) => {
// //   console.log(111)

// // })