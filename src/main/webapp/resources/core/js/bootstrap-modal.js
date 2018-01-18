!function ($) {

    "use strict"; // jshint ;_;


    /* MODAL CLASS DEFINITION
  * ====================== */

    var Modal = function (element, options) {
        this.options = options
        this.$element = $(element) //绑定关闭事件，关闭按钮要求有[data-dismiss="modal"]属性
        .delegate('[data-dismiss="modal"]', 'click.dismiss.modal', $.proxy(this.hide, this))
        this.options.remote && this.$element.find('.modal-body').load(this.options.remote)
    }

    Modal.prototype = {

        constructor: Modal

        , 
        toggle: function () {
            return this[!this.isShown ? 'show' : 'hide']()
        }

        , 
        show: function () {
            var that = this
            , e = $.Event('show')
            //触发show事件
            this.$element.trigger(e)

            if (this.isShown || e.isDefaultPrevented()) return

            this.isShown = true

            this.escape();//绑定或移除键盘事件

//            this.backdrop(fun
        }
    }
}