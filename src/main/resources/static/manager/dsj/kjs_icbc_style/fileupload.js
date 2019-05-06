jQuery.extend($.fn, {
        fileUpload: function(opts) {
            this.each(function() {
                var self = $(this);
                var quality = opts.quality ? opts.quality / 100 : 0.7;
                var dom = {
                    "fileToUpload": self,
                    "thumb": opts.thumb,
                    "progress": null
                };
                var Orientation = null;  
                var image = new Image(),
                    canvas = document.createElement("canvas"),
                    ctx = canvas.getContext('2d');
                var funs = {
                    setImageUrl: function(url) {
                        image.src = url;
                    },
                    bindEvent: function() {
                        console.log(dom.fileToUpload)
                        dom.fileToUpload.on("change", function() {
                            funs.fileSelect(this);
                        });
                    },
                    fileSelect: function(obj) {
                        var file = obj.files[0];
                        //获取照片方向角属性，用户旋转控制  
                        EXIF.getData(file, function() {  
                           // alert(EXIF.pretty(this));  
                            EXIF.getAllTags(this);   
                            //alert(EXIF.getTag(this, 'Orientation'));   
                            Orientation = EXIF.getTag(this, 'Orientation');  
                            //return;  
                        });  
                        var reader = new FileReader();
                        reader.onload = function() {
                            var url = reader.result;
                            funs.setImageUrl(url);
                            //dom.thumb.html(image);
                        };
                        image.onload = function() {
                            var mpImg = new MegaPixImage(image);
                            mpImg.render(canvas, {
                                maxWidth: 1080,
                                maxHeight: 1080,
                                quality: 1.0,
                                orientation: Orientation
                            });
                        funs.fileUpload();
                        };
                        reader.readAsDataURL(file);
                    },
                    fileUpload: function() {
                        var data = canvas.toDataURL("image/jpeg", quality);
                        //dataURL 的格式为 “data:image/png;base64,****”,逗号之前都是一些说明性的文字，我们只需要逗号之后的就行了
                        data = data.split(',')[1];
                        data = window.atob(data);
                        var ia = new Uint8Array(data.length);
                        for (var i = 0; i < data.length; i++) {
                            ia[i] = data.charCodeAt(i);
                        };
                        //canvas.toDataURL 返回的默认格式就是 image/png
                        var blob = new Blob([ia], {
                            type: "image/jpeg"
                        });
                        var fd = new FormData();
                        var name = self.attr("name");
                        var fileid = self.attr("id");
                        fd.append(name,blob,name+".jpg");
                        
                        
                        $('#'+fileid).after('<span id="file_upload_tip" style="color:red;">文件上传中, 请耐心等待...</span>');
                		$('#'+fileid).hide();
                        $.ajax({  
                            url: opts.url,  
                            type: 'POST',  
                            data: fd,  
                            async: false,  
                            cache: false,  
                            contentType: false,  
                            processData: false,  
                            success: function (res) { 
                            	$('#file_upload_tip').remove();
                				$('#'+fileid).show();
                            	opts.success(res);  
                            },
                            error: function (res) {  
                            	$('#file_upload_tip').remove();
                				$('#'+fileid).show();
                            }
                       }); 
                        
                        
                    }
                };
                funs.bindEvent();
            });
        }
    });
