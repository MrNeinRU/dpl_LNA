package ru.malygin.anytoany.data.enum_cls

enum class NetQueryMethods(
    val method: String
) {
    AUTH("auth"),
    GET_USER_INFO("get_user_info"),
    REMOTE_CHECK_TOKEN("remote_check_token"),
    GET_FACILITY_INFO("get_facility_info"),
    ;
}