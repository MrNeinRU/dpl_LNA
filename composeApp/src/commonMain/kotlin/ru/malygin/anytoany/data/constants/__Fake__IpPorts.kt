@file:Suppress("ClassName")
package ru.malygin.anytoany.data.constants

object __Fake__IpPorts {

    val ftpPorts = listOf(
        20, 21
    )

    val sshPorts = listOf(
        22
    )

    val webPorts = listOf(
        80
    )

    val remoteDesktopPorts = listOf(
        3389
    )


    fun List<Int>.toPorts(): String {
        return if (this.isNotEmpty()) this.toPortsList().joinToString(separator = ", ") else "?"
    }

    private fun List<Int>.toPortsList(): List<String> {
        return this.map { prt->
            listOfPorts.find { para->
                para.first == prt
            }?.second?.plus("($prt)") ?: run { "NoN($prt)"}
        }
    }

    private val listOfPorts: List<Pair<Int, String>> = listOf(
        20 to "FTP",
        21 to "FTP",
        22 to "SSH",
        80 to "HTTP",
        3389 to "RDP"
    )
}