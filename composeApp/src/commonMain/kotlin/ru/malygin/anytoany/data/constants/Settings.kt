package ru.malygin.anytoany.data.constants

import ru.malygin.anytoany.data.enum_cls.NetQueryMethods

object Settings {
    private const val METHOD = "method"

    const val TOKEN_VALID = false

    const val FACILITY_URL_LOCAL_HOST  = "localhost"

    private val FACILITY_URL_LOCAL_QUERY_List = NetQueryMethods.entries
        .map { it to (METHOD to it.method) }


    class GetEntryByQuery(
        private val query:NetQueryMethods
    ){
        private val ll = FACILITY_URL_LOCAL_QUERY_List.first {
            it.first == query
        }.second

        fun getFirst() = ll.first
        fun getSecond() = ll.second
    }

}