
<#macro htmlHeaders title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" href="/img/chrome.ico" type="image/x-icon" />
<title>${title}</title>
</#macro>

<#macro pageHead title='统一登录系统'>
<div class="header container" style="height: 50px;width: 100%;max-width: 100%">
    <div class="row">
        <div class="col-lg-2" style="line-height: 30px;font-size: 16px">
            <div class="pull-left" style="display: block">
                <a href="/" style="text-decoration: none;color: white">
                    <img src="/img/chrome.png" class="img-rounded" style="height: 30px;width: 30px;">
                    <span style="height: 40px;width: 40px;">${title}</span>
                </a>
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