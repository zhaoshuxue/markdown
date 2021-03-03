/**
 * 来自 https://github.com/mifunc/editor.md.uploadImg
 */

function initPasteDragImg(Editor) {
    var doc = document.getElementById(Editor.id)
    doc.addEventListener('paste', function (event) {
        event.preventDefault();
        // event.stopPropagation();
        var items = (event.clipboardData || window.clipboardData).items;
        var file = null;
        if (items && items.length) {
            // 搜索剪切板items
            // for (var i = 0; i < items.length; i++) {
            //     if (items[i].type.indexOf('image') !== -1) {
            //         file = items[i].getAsFile();
            //         break;
            //     }
            // }
            if (items[0].type.indexOf('image') !== -1) {
                file = items[0].getAsFile();
            }
        } else {
            console.log("当前浏览器不支持");
            return;
        }
        if (!file) {
            console.log("粘贴内容非图片");
            return;
        }
        uploadImg(file, Editor);
    });
    var dashboard = document.getElementById(Editor.id)
    dashboard.addEventListener("dragover", function (e) {
        e.preventDefault()
        e.stopPropagation()
    })
    dashboard.addEventListener("dragenter", function (e) {
        e.preventDefault()
        e.stopPropagation()
    })
    dashboard.addEventListener("drop", function (e) {
        e.preventDefault()
        e.stopPropagation()
        var files = this.files || e.dataTransfer.files;
        uploadImg(files[0], Editor);
    })
}

function uploadImg(file, Editor) {
    var formData = new FormData();
    // var fileName = new Date().getTime() + "." + file.name.split(".").pop();
    var fileName = new Date().getTime() + "." + file.type.split("/").pop();
    formData.append('editormd-image-file', file, fileName);
    $.ajax({
        type: 'post',
        url: Editor.settings.imageUploadURL,
        data: formData,
        processData: false,
        contentType: false,
        dataType: 'json',
        xhr: function xhr() {
            console.info("2222")
            var xhr = $.ajaxSettings.xhr()
            if (xhr.upload) {
                $("#barDiv").css("width", "0%")
                $("#progressDiv").show()
                xhr.upload.addEventListener('progress', uploadProgress, false)
            }
            return xhr;
        },
        success: function (msg) {
            var success = msg['success'];
            if (success == 1) {
                var url = msg["url"];
                if (/\.(png|jpg|jpeg|gif|bmp|ico)$/.test(url)) {
                    Editor.insertValue("![alt](" + msg["url"] + ")");
                } else {
                    Editor.insertValue("[下载附件](" + msg["url"] + ")");
                }
            } else {
                console.log(msg);
                alert("上传失败");
            }
        }
    });
}

function uploadProgress(e) {
    console.info("e   ", e)
    console.info("total=", e.total)
    console.info("loaded=", e.loaded)
    console.info("---")

    var num = parseInt(e.loaded / e.total * 100)
    console.info("---", num)

    $("#barDiv").css("width", num + "%")
    $("#barDiv").html(num + "%")

    if (num >= 100) {
        $("#progressDiv").hide()
    }


//    var div = document.createElement("div")
//    div.style.position = "fixed"
//    div.style.width = "100px"
//    div.style.height = "100px"
//    div.style.border = "5px solid red"
//    div.style.top = "80px"
//    div.style.left = "48%"
//    div.style.zIndex = "999999"
//    div.style.textAlign = "center"
//
//    var p = document.createElement("p")
//    p.innerHTML = "test";
//
//    div.appendChild(p)
//
//    document.body.appendChild(div)


}
