function createXMLHttpRequest() {
    try {
        xmlHttp = new XMLHttpRequest();
    } catch (tryMS) {
        try {
            xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (otherMS) {
            try {
                xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (failed) {
                xmlHttp = null;
                // 这里可以报一个错误，无法获得 XMLHttpRequest对象	
            }
        }
    }
    return xmlHttp;
}
