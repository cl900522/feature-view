
<#macro htmlHeaders title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${title}</title>
</#macro>

<#macro pageHead title='统一登录系统'>
<div class="header container" style="height: 60px;width: 100%;max-width: 100%">
    <div class="row">
        <div class="col-lg-2" style="line-height: 40px;font-size: 16px">
            <div class="pull-left" style="display: block">
                <img src="https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=117732031,468073355&fm=58&bpow=1024&bpoh=1024" class="img-rounded" style="height: 40px;width: 40px;">
                <span style="height: 40px;width: 40px;"><a href="/">${title}</a></span>
            </div>
        </div>
        <div class="col-lg-10" style="line-height: 40px;font-size: 16px">
            <div class="pull-right" style="display: block">
            </div>
        </div>
    </div>
</div>
<div class="progress" style="width: 100%;max-width: 100%;height: 10px">
    <div id="validateProcess" class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
        <span class="sr-only">x% 完成</span>
    </div>
</div>
</#macro>