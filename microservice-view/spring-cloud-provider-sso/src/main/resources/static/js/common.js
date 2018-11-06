function ProgressBar(barId) {
    this.barId = "";
    this.progressValue = 0;
    this.intervalTag = 0;
    this.resetValue = 0

    this.reset = function () {
        clearInterval(this.intervalTag);
        this.progressValue = this.resetValue;
        this.showProgress(this.progressValue);
    };
    this.go = function () {
        var me = this;
        me.intervalTag = setInterval(createGoInterVal(10), 500);
    };
    this.createGoInterVal =function(goStep) {
        var me = this;
        return function () {
            me.progressValue += Math.random() * goStep;
            if (me.progressValue > 100) {
                me.progressValue = 100;
                clearInterval(me.intervalTag);
            }
            console.log(me.progressValue);
            me.showProgress(me.progressValue);
        };
    }
    this.showProgress = function (progressValue) {
        $("#" + this.barId).css("width", progressValue + "%");
    };
    this.setProgress = function (progressValue) {
        if (progressValue <= 0) progressValue = 0;
        if (progressValue >= 100) progressValue = 100;
        clearInterval(this.intervalTag);
        this.progressValue = progressValue;
        this.showProgress(this.progressValue);
    }
    this.init = function (barId) {
        this.barId = barId;
    };
    this.init(barId);
}