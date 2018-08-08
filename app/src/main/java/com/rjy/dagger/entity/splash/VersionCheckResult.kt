package com.rjy.dagger.entity.splash

import com.rjy.http.entity.MessageEntity

class VersionCheckResult:MessageEntity() {

    var RES_FORCE_UPDATE:String="" // 更新方式 0-无需更新、1-非强制更新 2-强制更新
    var LATEST_VERS:String="" //最新版本
    var RES_PATH:String=""//资源路径
    var RES_URL:String=""

}