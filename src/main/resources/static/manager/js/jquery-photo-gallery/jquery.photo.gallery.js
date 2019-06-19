/*
 * @说明         : 本组件正式命名APPGO图片查看组件
 * @Description: file content
 * @Author     : tt
 * @LastEditors: tt
 * AppGo使用的 图片查看插件
 * Author       : lufeng@bingosoft.net
 * Version      : 1.0.6
 * @Date        : 2016-03-11 21: 59: 52
 * @LastEditTime: 2019-05-14 11:35:02
 */
(function ($) {

	var windowMargin = 8; //加多边距的宽高，使得图片看起来有边框效果

	//图片查看器
	$.fn.extend({

		photoGallery: function (options) {

			var isFirefox = navigator.userAgent.indexOf("Firefox") > -1;
			var MOUSEWHEEL_EVENT = isFirefox ? "DOMMouseScroll" : "mousewheel";
			var defaults = {
				//图片缩放倍率
				ratio: 1.2,
				//右下角缩略图宽度
				thumbnailsWidth: 180,
				//右下角缩略图高度
				thumbnailsHeight: 120,
				//HTML模版
				template: {
					//操作工具
					OPERTATION: '<div class="oper">' +
						'<span class="prev active"><i class="icon_tool-prev"></i></span>' +
						'<span class="next active" ><i class="icon_tool-next"></i></span>' +
						'</div>' +
						'<div class="tool active">' +
						'<div class="toolct">' +
						'<span class="oper_fullscreen" title="查看全屏"><i class="icon_tool-fullscreen"></i></span>' +
						'<span class="oper_bigger" title="放大图片"><i class="icon_tool-bigger"></i></span>' +
						'<span class="oper_smaller" title="缩小图片"><i class="icon_tool-smaller"></i></span>' +
						'<span class="oper_rotate" title="向右旋转"><i class="icon_tool-rotate"></i></span>' +
						'<span class="oper_download" title="下载图片"><i class="icon_tool-download"></i></span>' +
						'<span class="oper_open" title="新窗口打开图片" style="margin-right:0px;"><i class="icon_tool-open"></i></span>' +
						'</div>' +
						'</div>',
					//缩略图
					THUMBNAILS: "<div class='thumbnails'>" +
						'<span class="thumbClose" title="关闭缩略图"><i class="icon_close-small"></i></span>' +
						'<img ondragstart="return false;"/>' +
						'<div class="thumbDrag"><span></span></div>' +
						"</div>",
					//大图
					IMAGE: '<img class="image" ondragstart="return false;"/>'
				}
			};

			var o = $.extend(defaults, options),
				$gallery = $(this);
			////console.log(o);
			$gallery.append(o.template.OPERTATION).append(o.template.THUMBNAILS);

			var $tool = $(this).find(".tool"),
				$fullscreen = $(this).find(".oper_fullscreen"),
				$bigger = $(this).find(".oper_bigger"),
				$smaller = $(this).find(".oper_smaller"),
				$rotate = $(this).find(".oper_rotate"),
				$download = $(this).find(".oper_download"),
				$open = $(this).find(".oper_open"),
				$prev = $(this).find(".prev"),
				$next = $(this).find(".next"),
				$thumbnails = $(this).find(".thumbnails"),
				$image,
				$thumbImg,
				imageWidth,
				imageHeight,
				imgRatio,
				dragX,
				dragY,
				cW,
				cH,
				w, h, isVertical,
				thumbX,
				thumbY;

			//上一张
			$prev.on('click', function () {
				if (o.activeIndex > 0) {
					o.activeIndex--;
				} else {
					alert("已经到最前面一张了！");
				}
				toggleImage();
			}).on("mouseover", function (e) {
				if (o.activeIndex > 0)
					$(this).addClass("active");
			}).on("mouseout", function (e) {
				// $(this).removeClass("active");
			});

			//下一张
			$next.on('click', function () {
				if (o.activeIndex < o.imgs.length - 1) {
					o.activeIndex++;
				} else {
					alert("已经到最后一张了！");
				}
				toggleImage();
			}).on("mouseover", function (e) {
				if (o.activeIndex < o.imgs.length - 1)
					$(this).addClass("active");
			}).on("mouseout", function (e) {
				//$(this).removeClass("active");
			});

			//缩略图
			$thumbnails.css({
				height: o.thumbnailsHeight,
				width: o.thumbnailsWidth
			}).on("mouseenter", function (e) {
				thumbX = -1;
			}).on("mousedown", function (e) {
				thumbX = e.pageX || e.clientX;
				thumbY = e.pageY || e.clientY;

				cW = document.documentElement.clientWidth;
				cH = document.documentElement.clientHeight;
				e.stopPropagation();
			}).on("mousemove", function (e) {
				if (thumbX > 0) {
					var nextDragX = e.pageX || e.clientX;
					var nextDragY = e.pageY || e.clientY;
					var $td = $(this).find(".thumbDrag"),
						imageWidth = $image.width(),
						imageHeight = $image.height(),
						thumbImgWidth = $thumbImg.width(),
						thumbImgHeight = $thumbImg.height(),
						left = parseFloat($td.css("left")) + (nextDragX - thumbX),
						top = parseFloat($td.css("top")) + (nextDragY - thumbY),
						w = $td.width(),
						h = $td.height(),
						it,
						il,
						maxL,
						maxT;

					if (isVertical) {
						thumbImgWidth = [thumbImgHeight, thumbImgHeight = thumbImgWidth][0];
						imageWidth = [imageHeight, imageHeight = imageWidth][0];
					}
					it = (o.thumbnailsHeight - thumbImgHeight) / 2,
						il = (o.thumbnailsWidth - thumbImgWidth) / 2,
						maxL = o.thumbnailsWidth - w - il - 2, //减去2像素边框部分
						maxT = o.thumbnailsHeight - h - it - 2;

					if (left < il) left = il;
					else if (left > maxL) left = maxL;

					if (top < it) top = it;
					else if (top > maxT) top = maxT;

					$td.css({
						left: left,
						top: top
					});
					thumbX = nextDragX;
					thumbY = nextDragY;

					if (imageWidth < cW) left = (cW - imageWidth) / 2;
					else left = -imageWidth * (left - il) / thumbImgWidth;

					if (imageHeight < cH) top = (cH - imageHeight) / 2;
					else top = -imageHeight * (top - it) / thumbImgHeight;

					$image.offset({
						left: left,
						top: top
					});
				}
			}).on("mouseup", function (e) {
				thumbX = -1;
			});

			$thumbnails.find(".thumbClose").on("click", function () {
				$thumbnails.hide();
			});

			//显示工具栏
			$gallery.on("mouseover", function (e) {
				$tool.show();
			}).on("mouseenter", function (e) {
				dragX = -1;
			}).on("mouseout", function (e) {
				//$tool.hide();
			}).on("mousedown", function (e) {
				dragX = e.pageX || e.clientX;
				dragY = e.pageY || e.clientY;
				cW = document.documentElement.clientWidth;
				cH = document.documentElement.clientHeight;
				e.stopPropagation();
			}).on("mousemove", function (e) {
				if (dragX > 0) {
					var nextDragX = e.pageX || e.clientX;
					var nextDragY = e.pageY || e.clientY;
					var o = $image.offset(),
						left = o.left + (nextDragX - dragX),
						top = o.top + (nextDragY - dragY),
						w = $image.width(),
						h = $image.height();

					if (isVertical) {
						w = [h, h = w][0];
					}
					if (w > cW) {
						if (left > 0) {
							left = 0;
						} else if (left < cW - w) {
							left = cW - w;
						}
					} else {
						left = o.left;
					}
					if (h > cH) {
						if (top > 0) {
							top = 0;
						} else if (top < cH - h) {
							top = cH - h;
						}
					} else {
						top = o.top;
					}

					$image.offset({
						left: left,
						top: top
					});
					dragX = nextDragX;
					dragY = nextDragY;
					setThumbnails(); //缩略图拖拽点
				}
			}).on("mouseup", function (e) {
				dragX = -1;
			});

			//全屏
			var isMax, preWidth, preHeight, preTop, preLeft;
			$fullscreen.on("click", function () {
				var parentD = window.parent.document,
					J = $(parentD.getElementById("tid_ttjpgd"));
				J2 = $(parentD.getElementById("J_pg"));
				if (!isMax) {
					isMax = true;
					preWidth = document.documentElement.clientWidth;
					preHeight = document.documentElement.clientHeight;
					preTop = J.css("top");
					preLeft = J.css("left");
					J.css({
						top: 0,
						left: 0,
						width: parentD.body.clientWidth,
						height: parentD.body.clientHeight,
					});
					J2.css({
						top: 20,
						left: 0,
						width: parentD.body.clientWidth,
						height: parentD.body.clientHeight - 20,
					});
				} else {
					isMax = false;
					J.css({
						top: preTop,
						left: preLeft,
						width: preWidth,
						height: preHeight
					});
					J2.css({
						top: 20,
						left: 0,
						width: preWidth,
						height: preHeight - 20
					});
				}
			});

			//放大图片
			$bigger.on("click", function () {
				biggerImage();
			});

			//缩小图片
			$smaller.on("click", function () {
				smallerImage();
			});

			//旋转
			$rotate.on("click", function () {
				var rotateClass = $image.attr("class").match(/(rotate)(\d*)/);
				if (rotateClass) {
					var nextDeg = (rotateClass[2] * 1 + 90) % 360;
					$image.removeClass(rotateClass[0]).addClass("rotate" + nextDeg);
					$thumbImg.removeClass(rotateClass[0]).addClass("rotate" + nextDeg);
					resizeImage(nextDeg);
					resizeThumbImg(nextDeg);
					isVertical = nextDeg == 90 || nextDeg == 270;
				} else {
					$image.addClass("rotate90");
					$thumbImg.addClass("rotate90");
					resizeImage("90");
					resizeThumbImg("90");
					isVertical = true;
				}
			});

			function open(src, imgname) {
				window.open(src);
			}

			function download(src, imgname) {
				// $('#downImg').attr('src', src);
				var alink = document.createElement("a");
				alink.href = src;
				alink.download = imgname;
				alink.click();
			}
			//下载
			$download.on("click", function () {
				var imgUrl = $image.attr("src");
				if (!imgUrl) return;
				download(imgUrl, imgUrl.substring(imgUrl.lastIndexOf("/") + 1));
				//alert("没有找到兼容所有浏览器方法，所以暂不实现");
			});
			//打开
			$open.on("click", function () {
				var imgUrl = $image.attr("src");
				if (!imgUrl) return;
				open(imgUrl, imgUrl.substring(imgUrl.lastIndexOf("/") + 1));
				//alert("没有找到兼容所有浏览器方法，所以暂不实现");
			});

			$(window).on("resize", function () {
				setImagePosition();
			});

			if (document.attachEvent) {
				document.attachEvent("on" + MOUSEWHEEL_EVENT, function (e) {
					mouseWheelScroll(e);
				});
			} else if (document.addEventListener) {
				document.addEventListener(MOUSEWHEEL_EVENT, function (e) {
					mouseWheelScroll(e);
				}, false);
			}

			function mouseWheelScroll(e) {
				var _delta = parseInt(e.wheelDelta || -e.detail);
				//向上滚动
				if (_delta > 0) {
					biggerImage();
				}
				//向下滚动
				else {
					smallerImage();
				}
			}

			//键盘左右键
			document.onkeydown = function (e) {

				e = e || window.event;
				if (e.keyCode) {
					if (e.keyCode == 37) { //left
						if (o.activeIndex > 0) o.activeIndex--;
						toggleImage();
					}
					if (e.keyCode == 39) { //right
						if (o.activeIndex < o.imgs.length - 1) o.activeIndex++;
						toggleImage();
					}
				}
			};

			function init() {
				toggleImage();

				$(o.imgs).each(function (i, img) {
					$(o.template.IMAGE)
						.appendTo($gallery)
						.attr("src", img.url)
						.attr("index", i)
						.css({
							width: img.imgWidth,
							height: img.imgHeight,
							left: (cW - img.imgWidth) / 2,
							top: (cH - img.imgHeight) / 2
						}).on("dblclick", function () {
							app.window.close();
						});
				});
				$image = $(".image[index='" + o.activeIndex + "']", $gallery).addClass("active");
			}

			function toggleImage() {
				imageWidth = o.imgs[o.activeIndex].imgWidth;
				imageHeight = o.imgs[o.activeIndex].imgHeight;
				filename = o.imgs[o.activeIndex].url.substring(o.imgs[o.activeIndex].url.lastIndexOf("/") + 1);
				//console.log(imageWidth);
				imgRatio = imageWidth / imageHeight;
				cW = document.documentElement.clientWidth;
				cH = document.documentElement.clientHeight;
				$(".image", $gallery).removeClass("active");
				$image = $(".image[index='" + o.activeIndex + "']", $gallery).addClass("active").css({
					width: imageWidth,
					height: imageHeight
				}).removeClass("rotate0 rotate90 rotate180 rotate270");
				$thumbImg = $thumbnails.find("img").attr("src", o.imgs[o.activeIndex].url);
				$thumbnails.find("img").removeAttr("class").removeAttr("style");
				isVertical = false;
				$thumbnails.hide();
				//$prev.removeClass("active");
				//$next.removeClass("active");
				//tt add
				setImagePosition();
				var _parent = window.parent || window.top,
					_jg = _parent.document.getElementById("J_pg");
				_xmove_ttjpgj = _parent.document.getElementById("xmove_ttjpgj");
				var nown = parseInt(o.activeIndex, 0) + 1;
				s = "~拖拽我移动~" + filename + '_' + o.imgs[o.activeIndex].imgWidth_n + "X" + o.imgs[o.activeIndex].imgHeight_n + "(" + nown + "/" + o.imgs.length + ")";
				$(_xmove_ttjpgj).text(s);
			}


			function biggerImage() {
				var w = $image.width(),
					h = $image.height(),
					nextW = w * o.ratio,
					nextH = h * o.ratio;
				if (nextW - w < 1) nextW = Math.ceil(nextW);
				var percent = (nextW / imageWidth * 100).toFixed(0);
				if (percent > 90 && percent < 110) {
					percent = 100;
					nextW = imageWidth;
					nextH = imageHeight;
				} else if (percent > 1600) {
					percent = 1600;
					nextW = imageWidth * 16;
					nextH = imageHeight * 16;
				}

				$image.width(nextW).height(nextH);
				setImagePosition();
				showPercentTip(percent);
				showThumbnails(nextW, nextH);
			}

			function smallerImage() {
				var w = $image.width(),
					h = $image.height(),
					nextW,
					nextH;
				var percent = (w / o.ratio / imageWidth * 100).toFixed(0);
				if (percent < 5) {
					percent = 5;
					nextW = imageWidth / 20;
					nextH = imageHeight / 20;
				} else if (percent > 90 && percent < 110) {
					percent = 100;
					nextW = imageWidth;
					nextH = imageHeight;
				} else {
					nextW = w / o.ratio;
					nextH = h / o.ratio;
				}

				$image.width(nextW).height(nextH);
				setImagePosition();
				showPercentTip(percent);
				showThumbnails(nextW, nextH);
			}

			//显示缩略图
			function showThumbnails(width, height) {
				if (isVertical) width = [height, height = width][0];
				if (width > document.documentElement.clientWidth || height > document.documentElement.clientHeight) {
					$thumbnails.show();
					setThumbnails();
				} else {
					$thumbnails.hide();
				}
			}

			//重置图片宽高
			function resizeImage(rotateDeg) {

				var mH = document.documentElement.clientHeight - windowMargin,
					mW = document.documentElement.clientWidth - windowMargin;
				if (rotateDeg == '90' || rotateDeg == '270') {
					mW = [mH, mH = mW][0];
				}

				var width, height;
				width = Math.min(imageWidth, mW);
				height = Math.min(imageHeight, mH);

				if (width / height > imgRatio) {
					width = height * imgRatio;
				} else {
					height = width / imgRatio;
				}

				$image.css({
					width: width,
					height: height
				});
				setImagePosition();
			}

			function resizeThumbImg(rotateDeg) {
				var maxW = o.thumbnailsWidth,
					maxH = o.thumbnailsHeight;
				if (rotateDeg == '90' || rotateDeg == '270') {
					maxW = [maxH, maxH = maxW][0];
				}
				$thumbImg.css({
					maxWidth: maxW,
					maxHeight: maxH
				});
				$thumbnails.hide();
			}

			//显示百分比提示
			function showPercentTip(percent) {
				$gallery.find(".percentTip").remove();
				$("<div class='percentTip'><span>" + percent + "%</span></div>").appendTo($gallery).fadeOut(1500);
			}

			//设置图片位置
			function setImagePosition() {
				var w = $image.width(),
					h = $image.height(),
					cW = document.documentElement.clientWidth,
					cH = document.documentElement.clientHeight;

				var left = (cW - w) / 2,
					top = (cH - h) / 2;

				$image.css("left", left + "px").css("top", top + "px");
			}
			//设置缩略图拖拽区域
			function setThumbnails() {
				var $img = $thumbnails.find("img"),
					sW = $img.width(),
					sH = $img.height(),
					w = $image.width(),
					h = $image.height(),
					imf = $image.offset(),
					imfl = imf.left,
					imft = imf.top,
					cW = document.documentElement.clientWidth,
					cH = document.documentElement.clientHeight,
					tW,
					tH,
					tl,
					tt;

				if (isVertical) {
					sW = [sH, sH = sW][0];
					w = [h, h = w][0];
				}
				tW = sW / (w / cW);
				if (w < cW) tW = sW;
				tH = sH / (h / cH);
				if (h < cH) tH = sH;
				tl = (o.thumbnailsWidth - sW) / 2 + -imfl / w * sW;
				if (w < cW) tl = (o.thumbnailsWidth - sW) / 2;
				tt = (o.thumbnailsHeight - sH) / 2 + -imft / h * sH;
				if (h < cH) tt = (o.thumbnailsHeight - sH) / 2;
				$thumbnails.find(".thumbDrag").css({
					width: tW,
					height: tH,
					left: tl,
					top: tt
				});
			}

			init();
			return this;
		}
	});

	$.extend({
		//打开图片查看器
		openPhotoGallery: function (obj) {
			$(document).keyup(function (event) {
				switch (event.keyCode) {
					case 27:
						var parentD = document;
						//console.log($(parentD.body));
						$(parentD.body).removeClass('ovfHiden');
						$(parentD.html).removeClass('ovfHiden');
						var _parent = window.parent || window.top,
							_jg = _parent.document.getElementById("J_pg");
						_tid = _parent.document.getElementById("tid_ttjpgd");
						$(_jg).remove();
						$(_tid).remove();
						break;
				}
			});
			var $img = $(obj),
				imgUrl = $img[0].src;
			if ($img.eq(0).attr("gal-src") != undefined && $img.eq(0).attr("gal-src").length > 0) {
				imgUrl = $img.eq(0).attr("gal-src");
			}
			//console.log($img);
			if (!imgUrl) return;
			var gxw = $img.eq(0).attr("gal-width");
			var gxh = $img.eq(0).attr("gal-height");
			//HTML5提供了一个新属性naturalWidth/naturalHeight可以直接获取图片的原始宽高
			var img = $img[0],
				imgWidth = gxw != undefined ? gxw : img.naturalWidth,
				imgHeight = gxh != undefined ? gxh : img.naturalHeight,
				ratio = imgWidth / imgHeight,
				wH = 415,
				wW = 615,
				winHeight,
				winWidth,
				maxHeight = document.documentElement.clientHeight - windowMargin * 2 - 20,
				maxWidth = document.documentElement.clientWidth - windowMargin;
			winWidth = Math.max(wW, imgWidth);
			winHeight = Math.max(wH, imgHeight);
			var nRa = 1.0;
			var bWidthOver = false;
			var newMaxHeight = maxHeight;
			if (winWidth > maxWidth) {
				bWidthOver = true;
				var oldWinW = winWidth;
				winWidth = maxWidth;
				winHeight = Math.max(wH, Math.ceil(winWidth / ratio));
				nRa = maxWidth / oldWinW;
				newMaxHeight = Math.ceil(maxHeight * nRa);
				if (imgWidth > winWidth) {
					imgWidth = winWidth;
					imgHeight = Math.ceil(imgWidth / ratio);
				}
			}

			if (winHeight > maxHeight) {
				console.log(maxHeight);
				if (winHeight > newMaxHeight) {
					winHeight = newMaxHeight;
				} else {
					winHeight = maxHeight;
				}
				winWidth = Math.max(wW, Math.ceil(winHeight * ratio));
				if (imgHeight > winHeight) {
					imgHeight = winHeight;
					imgWidth = Math.ceil(imgHeight * ratio);
				}
				if (!bWidthOver) {
					maxWidth = Math.ceil(maxWidth * nRa);
				}
				if (winWidth > maxWidth) {
					winWidth = maxWidth;
					if (imgWidth > winWidth) {
						imgWidth = winWidth;
					}
				}
			}

			var $gallerys = $(obj).closest(".gallerys"),
				activeIndex = 0,
				imgs = [];
			$gallerys.find(".gallery-pic").each(function (i, elem) {
				var gxw = jQuery(this).attr("gal-width");
				var gxh = jQuery(this).attr("gal-height");
				var url = this.src,
					img = $(this)[0],
					nH = gxh != undefined ? gxh : img.naturalHeight,
					nW = gxw != undefined ? gxw : img.naturalWidth, //原始分辨率？？
					nHn = gxh != undefined ? gxh : img.naturalHeight,
					nWn = gxw != undefined ? gxw : img.naturalWidth, //原始分辨率？？
					ratio = nW / nH,
					w = nW,
					h = nH;
				if (jQuery(this).attr("gal-src") != undefined && jQuery(this).attr("gal-src").length > 0) {
					url = jQuery(this).attr("gal-src");
				}
				if (url == imgUrl) {
					activeIndex = i;
					w = imgWidth;
					h = imgHeight;
				} else {
					if (nW > winWidth) {
						w = winWidth;
						nH = h = Math.ceil(w / ratio);
						if (h > winHeight) {
							nH = h = winHeight;
							w = Math.ceil(h * ratio);
						}
					}
					if (nH > winHeight) {
						h = winHeight;
						w = Math.ceil(h * ratio);
						if (w > winWidth) {
							w = winWidth;
							h = Math.ceil(w / ratio);
						}
					}
				}
				imgs.push({
					url: url,
					imgHeight: h,
					imgWidth: w,
					imgHeight_n: nHn,
					imgWidth_n: nWn,
				});
			});
			//获取窗口可视范围的高度
			function getClientHeight() {
				var clientHeight = 0;
				if (document.documentElement.clientHeight && document.documentElement.clientHeight) {
					var clientHeight = (document.documentElement.clientHeight < document.documentElement.clientHeight) ? document.documentElement.clientHeight : document.documentElement.clientHeight;
				} else {
					var clientHeight = (document.documentElement.clientHeight > document.documentElement.clientHeight) ? document.documentElement.clientHeight : document.documentElement.clientHeight;
				}
				return clientHeight;
			};
			localStorage["photoGalleryImgs"] = JSON.stringify(imgs); //因为此字符串可能是base64字符，appgo无法传
			localStorage["photoGalleryActiveIndex"] = activeIndex;
			$("#tid_ttjpgd").remove();
			$("#xmove_ttjpgj").remove();
			$("#J_pg").remove();
			var nh = (document.documentElement.clientHeight - winHeight) / 2 - 260;
			var pos = "fixed";
			//console.log(getClientHeight());
			var ntop = (getClientHeight() - winHeight) / 2;
			var nleft = (document.documentElement.clientWidth - winWidth) / 2;
			var zoom = 1 / $("body").css("zoom");
			//开始添加  
			$("<div id='tid_ttjpgd'></div>").appendTo("body").css({
				'position': pos,
				left: nleft,
				top: ntop,
				width: winWidth,
				'text-align': 'center',
				'z-index': '2001',
				'zoom': zoom,
			});
			$("<label id='xmove_ttjpgj'>~拖拽我移动~</label>").appendTo("#tid_ttjpgd").css({
				'color': 'white',
				'width': '100%',
				'z-index': 2001,
				'cursor': 'move',
				'background': 'rgba(177, 178, 179, 0.6)',
				'border-top-left-radius': '4px',
				'border-top-right-radius': '4px'
			});
			$("<iframe></iframe>").appendTo("#tid_ttjpgd")
				.attr("id", "J_pg")
				.attr("src", "js/jquery-photo-gallery/gallery.html")
				.css({
					position: "absolute",
					left: 0,
					top: 20,
					width: '100%',
					height: winHeight - 20,
					background: 'rgba(177, 178, 179, 0.6)',
					border: '1px solid #6D6D6D',
					'border-bottom-right-radius': '4px',
					'border-bottom-left-radius': '4px',
					'z-index': 2000
				});
		},
		//做初始化
		initGallery: function () {
			var parentD = window.parent.document;
			//console.log($(parentD.body));
			$(parentD.body).addClass('ovfHiden');
			$(parentD.html).addClass('ovfHiden');
			var activeIndex = localStorage["photoGalleryActiveIndex"],
				imgs = JSON.parse(localStorage["photoGalleryImgs"]);

			localStorage.removeItem("photoGalleryActiveIndex");
			localStorage.removeItem("photoGalleryImgs");

			$(".gallery").photoGallery({
				imgs: imgs,
				activeIndex: activeIndex
			});

			$(".closeWin").click(function () {
				var parentD = window.parent.document;
				//console.log($(parentD.body));
				$(parentD.body).removeClass('ovfHiden');
				$(parentD.html).removeClass('ovfHiden');
				var _parent = window.parent || window.top,
					_jg = _parent.document.getElementById("J_pg");
				_tid = _parent.document.getElementById("tid_ttjpgd");
				$(_jg).remove();
				$(_tid).remove();
			});
			var _parents = window.parent || window.top,
				_stid = _parents.document.getElementById("xmove_ttjpgj");
			_stidup = _parents.document.getElementById("tid_ttjpgd");
			_jgs = _parents.document.getElementById("J_pg");
			$(_stid).mousedown(function (e) {
				var isMove = true;
				var div_x = e.clientX - $(_stidup).offset().left;
				var div_y = e.clientY - $(_stidup).offset().top + $(_parents).scrollTop();
				$(_parents.document).mousemove(function (e) {
					if (isMove) {
						var obj = $(_stidup);
						obj.css({
							"left": e.clientX - div_x < 2 ? 2 : e.clientX - div_x,
							"top": e.clientY - div_y < 2 ? 2 : e.clientY - div_y
						});
					}
				}).mouseup(
					function () {
						isMove = false;
					});
				$(document).mousemove(function (e) {
					if (isMove) {
						var obj = $(_stidup);
						var newTop = e.clientY - div_y + $(_stidup).offset().top <= 2 ? 2 : e.clientY - div_y + $(_stidup).offset().top + $(_parents).scrollTop();
						var newLeft = e.clientX - div_x + $(_stidup).offset().left < 2 ? 2 : e.clientX - div_x + $(_stidup).offset().left;
						obj.css({
							"left": newLeft,
							"top": newTop
						});
					}
				}).mouseup(
					function () {
						isMove = false;
					});
			});
		}
	});

})(jQuery);