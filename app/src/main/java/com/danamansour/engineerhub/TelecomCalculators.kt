package com.danamansour.engineerhub

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.log10
import kotlin.math.pow

@Composable
fun SubnetCalculator() {
    var ipAddress by remember { mutableStateOf("") }
    var cidrInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "IPv4 Subnet Calculator",
        subtitle = "Calculates Network, Broadcast & Host Range",
        onCalculate = {
            val cidr = cidrInput.trim().toIntOrNull()
            val ipParts = ipAddress.trim().split(".")
            if (cidr != null && cidr in 0..32 && ipParts.size == 4 && ipParts.all { it.toIntOrNull() in 0..255 }) {
                val ipInt = ipParts.fold(0L) { acc, part -> (acc shl 8) + part.toLong() }
                val maskInt = if (cidr == 0) 0L else (0xFFFFFFFFL shl (32 - cidr)) and 0xFFFFFFFFL
                val netInt = ipInt and maskInt
                val broadcastInt = netInt or (maskInt.inv() and 0xFFFFFFFFL)

                fun longToIp(num: Long) = "${(num shr 24) and 255}.${(num shr 16) and 255}.${(num shr 8) and 255}.${num and 255}"

                val netAddr = longToIp(netInt)
                val broadAddr = longToIp(broadcastInt)
                val totalHosts = if (cidr >= 31) 0 else (1L shl (32 - cidr)) - 2

                val hostRange = if (cidr in 1..30) {
                    "${longToIp(netInt + 1)} - ${longToIp(broadcastInt - 1)}"
                } else "N/A"

                result = "Network: $netAddr/$cidr\nBroadcast: $broadAddr\nHost Range: $hostRange\nUsable Hosts: $totalHosts"
                isError = false
            } else {
                result = "Invalid IP or CIDR (0-32)"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            EngineerTextField(value = ipAddress, onValueChange = { ipAddress = it }, label = "IP (e.g. 192.168.1.10)", isTextOnly = true)
            EngineerTextField(value = cidrInput, onValueChange = { cidrInput = it }, label = "CIDR (0-32)")
        }
    }
}

@Composable
fun ShannonCapacityCalculator() {
    var bandwidth by remember { mutableStateOf("") }
    var snrDb by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Shannon-Hartley Capacity",
        subtitle = "C = B · log₂(1 + SNR)",
        onCalculate = {
            val b = bandwidth.toDoubleOrNull()
            val db = snrDb.toDoubleOrNull()
            if (b != null && db != null && b > 0) {
                val snrLinear = 10.0.pow(db / 10.0)
                val capacityBps = b * (log10(1.0 + snrLinear) / log10(2.0))
                val capacityMbps = capacityBps / 1e6
                result = "Capacity (C): ${String.format("%.2f", capacityMbps)} Mbps\n(${String.format("%.0f", capacityBps)} bps)"
                isError = false
            } else {
                result = "Invalid Input"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            EngineerTextField(value = bandwidth, onValueChange = { bandwidth = it }, label = "Bandwidth B (Hz)")
            EngineerTextField(value = snrDb, onValueChange = { snrDb = it }, label = "SNR (dB)")
        }
    }
}

@Composable
fun FSPLCalculator() {
    var distanceKm by remember { mutableStateOf("") }
    var freqMhz by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Free Space Path Loss (FSPL)",
        subtitle = "FSPL(dB) = 20log₁₀(d) + 20log₁₀(f) + 32.44",
        onCalculate = {
            val d = distanceKm.toDoubleOrNull()
            val f = freqMhz.toDoubleOrNull()
            if (d != null && f != null && d > 0 && f > 0) {
                val fspl = (20 * log10(d)) + (20 * log10(f)) + 32.44
                result = "Path Loss: ${String.format("%.2f", fspl)} dB"
                isError = false
            } else {
                result = "Invalid Input"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            EngineerTextField(value = distanceKm, onValueChange = { distanceKm = it }, label = "Distance d (km)")
            EngineerTextField(value = freqMhz, onValueChange = { freqMhz = it }, label = "Frequency f (MHz)")
        }
    }
}

@Composable
fun DataTransferCalculator() {
    var fileSizeMB by remember { mutableStateOf("") }
    var speedMbps by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Data Transfer Time",
        subtitle = "t = File Size / Transfer Rate",
        onCalculate = {
            val sizeMb = fileSizeMB.toDoubleOrNull()
            val speed = speedMbps.toDoubleOrNull()
            if (sizeMb != null && speed != null && sizeMb > 0 && speed > 0) {
                val sizeBits = sizeMb * 8.0 * 1024.0 * 1024.0
                val speedBitsPerSec = speed * 1e6
                val totalSeconds = sizeBits / speedBitsPerSec

                val mins = (totalSeconds / 60).toInt()
                val secs = (totalSeconds % 60).toInt()

                result = "Transfer Time: ${String.format("%.2f", totalSeconds)} sec\n(Approx. ${mins}m ${secs}s)"
                isError = false
            } else {
                result = "Invalid Input"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            EngineerTextField(value = fileSizeMB, onValueChange = { fileSizeMB = it }, label = "File Size (MB)")
            EngineerTextField(value = speedMbps, onValueChange = { speedMbps = it }, label = "Speed (Mbps)")
        }
    }
}

@Composable
fun DbmWattsCalculator() {
    var dbmInput by remember { mutableStateOf("") }
    var wattsInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "dBm ↔ Watts Converter",
        subtitle = "P(dBm) = 10 · log₁₀(1000 · P(W))",
        onCalculate = {
            val dbm = dbmInput.toDoubleOrNull()
            val w = wattsInput.toDoubleOrNull()

            if (dbm != null) {
                val pWatts = 10.0.pow((dbm - 30) / 10.0)
                val pMilliwatts = pWatts * 1000.0
                result = "Power: ${String.format("%.6f", pWatts)} W\n(${String.format("%.3f", pMilliwatts)} mW)"
                isError = false
            } else if (w != null && w > 0) {
                val pDbm = 10 * log10(w * 1000.0)
                result = "Power: ${String.format("%.2f", pDbm)} dBm"
                isError = false
            } else {
                result = "Enter dBm OR Watts"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            EngineerTextField(value = dbmInput, onValueChange = { dbmInput = it }, label = "Power (dBm)")
            EngineerTextField(value = wattsInput, onValueChange = { wattsInput = it }, label = "Power (Watts)")
        }
    }
}

@Composable
fun LogicGateCalculator() {
    var inputA by remember { mutableStateOf("") }
    var inputB by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    EngineerCalculatorTemplate(
        title = "Logic Gate Truth Evaluator",
        subtitle = "Evaluates AND, OR, XOR, NAND, NOR, XNOR",
        onCalculate = {
            val aInt = inputA.trim().toIntOrNull()
            val bInt = inputB.trim().toIntOrNull()

            if (aInt != null && bInt != null && (aInt == 0 || aInt == 1) && (bInt == 0 || bInt == 1)) {
                val a = aInt == 1
                val b = bInt == 1

                val andRes = if (a && b) 1 else 0
                val orRes = if (a || b) 1 else 0
                val xorRes = if (a xor b) 1 else 0
                val nandRes = if (!(a && b)) 1 else 0
                val norRes = if (!(a || b)) 1 else 0

                result = "AND: $andRes  |  OR: $orRes  |  XOR: $xorRes\nNAND: $nandRes | NOR: $norRes"
                isError = false
            } else {
                result = "Enter 0 or 1 for inputs"
                isError = true
            }
        },
        resultText = result,
        isError = isError
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            EngineerTextField(value = inputA, onValueChange = { inputA = it }, label = "Input A (0 or 1)")
            EngineerTextField(value = inputB, onValueChange = { inputB = it }, label = "Input B (0 or 1)")
        }
    }
}