var disabledImage = 'resources/button3.gif';
var enabledImage = 'resources/button2.gif';

function buttonpush(buttonName) {
    var button = document.getElementById(buttonName);
    if (!button.disabled) {
        button.src = disabledImage;
        button.disabled = true;
    }
    var txt = document.createTextNode(buttonName);
    addCell(txt);
} 

function buttonpop(buttonName) {
    var txt = document.createTextNode(buttonName);
    removeCell(txt);
} 

function addCell(cellData) {
    var cell = document.getElementById("tr1").insertCell(0);
    cell.setAttribute("height", "50px");
    cell.setAttribute("width", "50px");
    cell.innerHTML = cellData.nodeValue;
    cell.className = "queueCell";
}

function removeCell(cellData) {
    var row = document.getElementById("tr1");
    var cells = row.getElementsByTagName("td");
    if (typeof cells != 'undefined' || cells != null) {
        for (var i=0; i<cells.length; i++) {
            if (cells[i].firstChild.nodeValue == cellData.nodeValue) {
                row.deleteCell(i);
                var button = document.getElementById(cellData.nodeValue);
                button.disabled = false;
                button.src = enabledImage;
                break;
            }
        }
    }
}

function errorMsg(eventName, data) {
    alert("Name: "+eventName+" Error Status: "+data.statusMessage);
}

// Listen for all queue events
//OpenAjax.hub.subscribe("javax.faces.Event.**",msg);
// Listen for all error events
//OpenAjax.hub.subscribe("javax.faces.Error.**",errorMsg);