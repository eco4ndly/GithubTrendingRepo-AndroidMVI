package com.eco4ndly.githubtrendingrepo.common.exception

import com.eco4ndly.githubtrendingrepo.data.api.NetworkErrorMessages

/**
 * A Sayan Porya code on 2020-02-08
 */
class NoDataException(message: String? = NetworkErrorMessages.UNKNOWN_ERROR) : Throwable(message)