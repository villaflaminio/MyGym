# Creazione Desktop Applcation

ll codice mostrato è il *main.js* che permette la creazione e gestione del cuore dell’applicazione Desktop, infatti il mainWindow ricerca l’*index.html* e lo carica nella finestra principale.

La potenza sta proprio nel fatto che l’index.html è direttamente collegato ad Angular

```tsx
// BrowserWindows e App permettono la gestione del ciclo di vita dell'applicativo e la creazione della finestra da
const {app, BrowserWindow} = require('electron') 
    const url = require("url");
    const path = require("path");

    let mainWindow

    function createWindow () {
      mainWindow = new BrowserWindow({     //creazione finestra dell'applicaione
        width: 800,
        height: 600,
        webPreferences: {
          nodeIntegration: true
        }
      })

      mainWindow.loadURL(
        url.format({
          pathname: path.join(__dirname, `/dist/index.html`),  //stiamo dicendo a Electron di cercare l'index.html
          protocol: "file:",
          slashes: true
        })
      );
      // Open the DevTools.
      mainWindow.webContents.openDevTools()

      mainWindow.on('closed', function () {
        mainWindow = null
      })
    }
		// lancia l'applicazione
    app.on('ready', createWindow) 

		// eventuali operazioni da effettuare alla chiusura dell'applcativo
    app.on('window-all-closed', function () {  
      if (process.platform !== 'darwin') app.quit()
    })

    app.on('activate', function () {
      if (mainWindow === null) createWindow()
    })
```