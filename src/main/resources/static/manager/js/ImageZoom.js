var ImageZoom = function (image, viewer, options) {
    this._initialize(image, viewer, options);
    this._initLoad();
};

ImageZoom.prototype = {
    //��ʼ������
    _initialize: function (image, viewer, options) {
        this._image = $$(image);//ԭͼ
        this._zoom = document.createElement("img");//��ʾͼ
        this._viewer = $$(viewer);//��ʾ��
        this._viewerWidth = 0;//��ʾ���
        this._viewerHeight = 0;//��ʾ���
        this._preload = new Image();//Ԥ�ض���
        this._rect = null;//ԭͼ����
        this._repairLeft = 0;//��ʾͼx��������
        this._repairTop = 0;//��ʾͼy��������
        this._rangeWidth = 0;//��ʾ��Χ���
        this._rangeHeight = 0;//��ʾ��Χ�߶�
        this._timer = null;//��ʱ��
        this._loaded = false;//�Ƿ����
        this._substitute = false;//�Ƿ��滻

        var opt = this._setOptions(options);

        this._scale = opt.scale;
        this._max = opt.max;
        this._min = opt.min;
        this._originPic = opt.originPic;
        this._zoomPic = opt.zoomPic;
        this._rangeWidth = opt.rangeWidth;
        this._rangeHeight = opt.rangeHeight;

        this.delay = opt.delay;
        this.autoHide = opt.autoHide;
        this.mouse = opt.mouse;
        this.rate = opt.rate;

        this.onLoad = opt.onLoad;
        this.onStart = opt.onStart;
        this.onMove = opt.onMove;
        this.onEnd = opt.onEnd;

        var oThis = this, END = function () {
            oThis._end();
        };
        this._END = function () {
            oThis._timer = setTimeout(END, oThis.delay);
        };
        this._START = $$F.bindAsEventListener(this._start, this);
        this._MOVE = $$F.bindAsEventListener(this._move, this);
        this._MOUSE = $$F.bindAsEventListener(this._mouse, this);
        this._OUT = $$F.bindAsEventListener(function (e) {
            if (!e.relatedTarget) this._END();
        }, this);

        $$CE.fireEvent(this, "init");
    },
    //����Ĭ������
    _setOptions: function (options) {
        this.options = {//Ĭ��ֵ
            scale: 0,//����(��ͼ/ԭͼ)
            max: 10,//������
            min: 1.5,//��С����
            originPic: "",//ԭͼ��ַ
            zoomPic: "",//��ͼ��ַ
            rangeWidth: 0,//��ʾ��Χ���
            rangeHeight: 0,//��ʾ��Χ�߶�
            delay: 20,//�ӳٽ���ʱ��
            autoHide: true,//�Ƿ��Զ�����
            mouse: false,//�������
            rate: .2,//������ű���
            onLoad: $$.emptyFunction,//�������ʱִ��
            onStart: $$.emptyFunction,//��ʼ�Ŵ�ʱִ��
            onMove: $$.emptyFunction,//�Ŵ��ƶ�ʱִ��
            onEnd: $$.emptyFunction//�Ŵ����ʱִ��
        };
        return $$.extend(this.options, options || {});
    },
    //��ʼ������
    _initLoad: function () {
        var image = this._image, originPic = this._originPic,
            useOrigin = !this._zoomPic && this._scale,
            loadImage = $$F.bind(useOrigin ? this._loadOriginImage : this._loadImage, this);
        //�����Զ�����
        this.autoHide && this._hide();
        //�ȼ���ԭͼ
        if (originPic && originPic != image.src) {//ʹ���Զ����ַ
            image.onload = loadImage;
            image.src = originPic;
        } else if (image.src) {//ʹ��Ԫ�ص�ַ
            if (!image.complete) {//δ������
                image.onload = loadImage;
            } else {//�Ѿ�����
                loadImage();
            }
        } else {
            return;//û��ԭͼ��ַ
        }
        //���ش�ͼ
        if (!useOrigin) {
            var preload = this._preload, zoomPic = this._zoomPic || image.src,
                loadPreload = $$F.bind(this._loadPreload, this);
            if (zoomPic != preload.src) {//�µ�ַ���¼���
                preload.onload = loadPreload;
                preload.src = zoomPic;
            } else {//���ڼ���
                if (!preload.complete) {//δ������
                    preload.onload = loadPreload;
                } else {//�Ѿ�����
                    this._loadPreload();
                }
            }
        }
    },
    //ԭͼ�Ŵ���س���
    _loadOriginImage: function () {
        this._image.onload = null;
        this._zoom.src = this._image.src;
        this._initLoaded();
    },
    //ԭͼ���س���
    _loadImage: function () {
        this._image.onload = null;
        if (this._loaded) {//��ͼ�Ѿ�����
            this._initLoaded();
        } else {
            this._loaded = true;
            if (this._scale) {//���Զ����������ԭͼ�Ŵ��滻��ͼ
                this._substitute = true;
                this._zoom.src = this._image.src;
                this._initLoaded();
            }
        }
    },
    //��ͼԤ�س���
    _loadPreload: function () {
        this._preload.onload = null;
        this._zoom.src = this._preload.src;
        if (this._loaded) {//ԭͼ�Ѿ�����
            //û��ʹ���滻
            if (!this._substitute) {
                this._initLoaded();
            }
        } else {
            this._loaded = true;
        }
    },
    //��ʼ����������
    _initLoaded: function (src) {
        //��ʼ����ʾͼ
        this._initSize();
        //��ʼ����ʾ��
        this._initViewer();
        //��ʼ������
        this._initData();
        //��ʼִ��
        $$CE.fireEvent(this, "load");
        this.onLoad();
        this.start();
    },
    //��ʼ����ʾͼ�ߴ�
    _initSize: function () {
        var zoom = this._zoom, image = this._image, scale = this._scale;
        if (!scale) {
            scale = this._preload.width / image.width;
        }
        this._scale = scale = Math.min(Math.max(this._min, scale), this._max);
        //������������ʾͼ��С
        zoom.width = Math.ceil(image.width * scale);
        zoom.height = Math.ceil(image.height * scale);
    },
    //��ʼ����ʾ��
    _initViewer: function () {
        var zoom = this._zoom, viewer = this._viewer;
        //������ʽ
        var styles = {padding: 0, overflow: "hidden"}, p = $$D.getStyle(viewer, "position");
        if (p != "relative" && p != "absolute") {
            styles.position = "relative";
        }
        ;
        $$D.setStyle(viewer, styles);
        zoom.style.position = "absolute";
        //������ʾͼ
        if (!$$D.contains(viewer, zoom)) {
            viewer.appendChild(zoom);
        }
    },
    //��ʼ������
    _initData: function () {
        var zoom = this._zoom, image = this._image, viewer = this._viewer,
            scale = this._scale, rangeWidth = this._rangeWidth, rangeHeight = this._rangeHeight;
        //ԭͼ����
        this._rect = $$D.rect(image);
        //��������
        this._repairLeft = image.clientLeft + parseInt($$D.getStyle(image, "padding-left"));
        this._repairTop = image.clientTop + parseInt($$D.getStyle(image, "padding-top"));
        //���÷�Χ��������ʾ���С
        if (rangeWidth > 0 && rangeHeight > 0) {
            rangeWidth = Math.ceil(rangeWidth);
            rangeHeight = Math.ceil(rangeHeight);
            this._viewerWidth = Math.ceil(rangeWidth * scale);
            this._viewerHeight = Math.ceil(rangeHeight * scale);
            $$D.setStyle(viewer, {
                width: this._viewerWidth + "px",
                height: this._viewerHeight + "px"
            });
        } else {
            var styles;
            if (!viewer.clientWidth) {//����
                var style = viewer.style;
                styles = {
                    display: style.display,
                    position: style.position,
                    visibility: style.visibility
                };
                $$D.setStyle(viewer, {
                    display: "block", position: "absolute", visibility: "hidden"
                });
            }
            this._viewerWidth = viewer.clientWidth;
            this._viewerHeight = viewer.clientHeight;
            if (styles) {
                $$D.setStyle(viewer, styles);
            }

            rangeWidth = Math.ceil(this._viewerWidth / scale);
            rangeHeight = Math.ceil(this._viewerHeight / scale);
        }
        this._rangeWidth = rangeWidth;
        this._rangeHeight = rangeHeight;
    },
    //��ʼ
    _start: function () {
        clearTimeout(this._timer);
        var viewer = this._viewer, image = this._image, scale = this._scale;
        viewer.style.display = "block";
        $$CE.fireEvent(this, "start");
        this.onStart();
        $$E.removeEvent(image, "mouseover", this._START);
        $$E.removeEvent(image, "mousemove", this._START);
        $$E.addEvent(document, "mousemove", this._MOVE);
        $$E.addEvent(document, "mouseout", this._OUT);
        this.mouse && $$E.addEvent(document, $$B.firefox ? "DOMMouseScroll" : "mousewheel", this._MOUSE);
    },
    //�ƶ�
    _move: function (e) {
        clearTimeout(this._timer);
        var x = e.pageX, y = e.pageY, rect = this._rect;
        if (x < rect.left || x > rect.right || y < rect.top || y > rect.bottom) {
            this._END();//�Ƴ�ԭͼ��Χ
        } else {
            var pos = {}, scale = this._scale, zoom = this._zoom,
                viewerWidth = this._viewerWidth,
                viewerHeight = this._viewerHeight;
            //��������
            pos.left = viewerWidth / 2 - (x - rect.left - this._repairLeft) * scale;
            pos.top = viewerHeight / 2 - (y - rect.top - this._repairTop) * scale;

            $$CE.fireEvent(this, "repair", e, pos);
            //��Χ����
            x = Math.ceil(Math.min(Math.max(pos.left, viewerWidth - zoom.width), 0));
            y = Math.ceil(Math.min(Math.max(pos.top, viewerHeight - zoom.height), 0));
            //���ö�λ
            zoom.style.left = x + "px";
            zoom.style.top = y + "px";

            $$CE.fireEvent(this, "move", e, x, y);
            this.onMove();
        }
    },
    //����
    _end: function () {
        $$CE.fireEvent(this, "end");
        this.onEnd();
        this.autoHide && this._hide();
        this.stop();
        this.start();
    },
    //����
    _hide: function () {
        this._viewer.style.display = "none";
    },
    //�������
    _mouse: function (e) {
        this._scale += (e.wheelDelta ? e.wheelDelta / (-120) : (e.detail || 0) / 3) * this.rate;

        var opt = this.options;
        this._rangeWidth = opt.rangeWidth;
        this._rangeHeight = opt.rangeHeight;

        this._initSize();
        this._initData();
        this._move(e);
        e.preventDefault();
    },
    //��ʼ
    start: function () {
        if (this._viewerWidth && this._viewerHeight) {
            var image = this._image, START = this._START;
            $$E.addEvent(image, "mouseover", START);
            $$E.addEvent(image, "mousemove", START);
        }
    },
    //ֹͣ
    stop: function () {
        clearTimeout(this._timer);
        $$E.removeEvent(this._image, "mouseover", this._START);
        $$E.removeEvent(this._image, "mousemove", this._START);
        $$E.removeEvent(document, "mousemove", this._MOVE);
        $$E.removeEvent(document, "mouseout", this._OUT);
        $$E.removeEvent(document, $$B.firefox ? "DOMMouseScroll" : "mousewheel", this._MOUSE);
    },
    //�޸�����
    reset: function (options) {
        this.stop();

        var viewer = this._viewer, zoom = this._zoom;
        if ($$D.contains(viewer, zoom)) {
            viewer.removeChild(zoom);
        }

        var opt = $$.extend(this.options, options || {});
        this._scale = opt.scale;
        this._max = opt.max;
        this._min = opt.min;
        this._originPic = opt.originPic;
        this._zoomPic = opt.zoomPic;
        this._rangeWidth = opt.rangeWidth;
        this._rangeHeight = opt.rangeHeight;

        //��������
        this._loaded = this._substitute = false;
        this._rect = null;
        this._repairLeft = this._repairTop =
            this._viewerWidth = this._viewerHeight = 0;

        this._initLoad();
    },
    //���ٳ���
    dispose: function () {
        $$CE.fireEvent(this, "dispose");
        this.stop();
        if ($$D.contains(this._viewer, this._zoom)) {
            this._viewer.removeChild(this._zoom);
        }
        this._image.onload = this._preload.onload =
            this._image = this._preload = this._zoom = this._viewer =
                this.onLoad = this.onStart = this.onMove = this.onEnd =
                    this._START = this._MOVE = this._END = this._OUT = null
    }
}

ImageZoom._MODE = {
    //�ϱ�
    "handle": {
        options: {//Ĭ��ֵ
            handle: ""//�ϱ�����
        },
        methods: {
            init: function () {
                var handle = $$(this.options.handle);
                if (!handle) {//û�ж���Ļ��ø�����ʾ�����
                    var body = document.body;
                    handle = body.insertBefore(this._viewer.cloneNode(false), body.childNodes[0]);
                    handle.id = "";
                    handle["_createbyhandle"] = true;//���ɱ�ʶ�����Ƴ�
                }
                $$D.setStyle(handle, {padding: 0, margin: 0, display: "none"});

                this._handle = handle;
                this._repairHandleLeft = 0;//��������left
                this._repairHandleTop = 0;//��������top
            },
            load: function () {
                var handle = this._handle, rect = this._rect;
                $$D.setStyle(handle, {
                    position: "absolute",
                    width: this._rangeWidth + "px",
                    height: this._rangeHeight + "px",
                    display: "block",
                    visibility: "hidden"
                });
                //��ȡ��������
                this._repairHandleLeft = rect.left + this._repairLeft - handle.clientLeft;
                this._repairHandleTop = rect.top + this._repairTop - handle.clientTop;
                //����offsetParentλ��
                if (!/BODY|HTML/.test(handle.offsetParent.nodeName)) {
                    var parent = handle.offsetParent, rect = $$D.rect(parent);
                    this._repairHandleLeft -= rect.left + parent.clientLeft;
                    this._repairHandleTop -= rect.top + parent.clientTop;
                }
                //����
                $$D.setStyle(handle, {display: "none", visibility: "visible"});
            },
            start: function () {
                this._handle.style.display = "block";
            },
            move: function (e, x, y) {
                var style = this._handle.style, scale = this._scale;
                style.left = Math.ceil(this._repairHandleLeft - x / scale) + "px";
                style.top = Math.ceil(this._repairHandleTop - y / scale) + "px";
            },
            end: function () {
                this._handle.style.display = "none";
            },
            dispose: function () {
                if ("_createbyhandle" in this._handle) {
                    document.body.removeChild(this._handle);
                }
                this._handle = null;
            }
        }
    }
}

ImageZoom.prototype._initialize = (function () {
    var init = ImageZoom.prototype._initialize,
        mode = ImageZoom._MODE,
        modes = {
            "handle": [mode.handle]
        };
    return function () {
        var options = arguments[2];
        if (options && options.mode && modes[options.mode]) {
            $$A.forEach(modes[options.mode], function (mode) {
                //��չoptions
                $$.extend(options, mode.options, false);
                //��չ����
                $$A.forEach(mode.methods, function (method, name) {
                    $$CE.addEvent(this, name, method);
                }, this);
            }, this);
        }
        init.apply(this, arguments);
    }
})();