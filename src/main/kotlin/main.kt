fun main() {
    println(transferCommission("MasterCard", 75000, 150000))
}

fun transferCommission(
    cardType: String = "MIR",
    totalTransfersThisMonth: Int = 0,
    transferAmount: Int
): String {
    val dailyLimit = 150000
    val monthlyLimit = 600000
    val monthlyMasterCardLimit = 75000
    val masterCardCommissionRate = 0.006
    val visaCommissionRate = 0.0075
    val visaMinCommission = 35

    // Проверка превышения месячного лимита
    if (totalTransfersThisMonth + transferAmount > monthlyLimit) {
        return "Превышен месячный лимит"
    }

    // Проверка превышения суточного лимита
    if (transferAmount > dailyLimit) {
        return "Превышен суточный лимит"
    }

    // Расчет комиссии в зависимости от типа карты
    return when (cardType) {
        "MasterCard" -> {
            val totalTransfersAfterThisTransfer = totalTransfersThisMonth + transferAmount
            val commission =
                if (totalTransfersThisMonth <= monthlyMasterCardLimit && totalTransfersAfterThisTransfer > monthlyMasterCardLimit) {
                    val exceedingAmount = totalTransfersAfterThisTransfer - monthlyMasterCardLimit
                    val commissionWithoutExceeding = (exceedingAmount * masterCardCommissionRate).toInt()
                    val fixedCommission =
                        if (commissionWithoutExceeding + 20 > transferAmount) transferAmount else commissionWithoutExceeding + 20
                    fixedCommission
                } else {
                    0
                }
            "Транзакция выполнена успешно. Комиссия: $commission"
        }

        "Visa" -> {
            val commission = (transferAmount * visaCommissionRate).toInt()
            val finalCommission = if (commission < visaMinCommission) visaMinCommission else commission
            "Транзакция выполнена успешно. Комиссия: $finalCommission"
        }

        "MIR" -> {
            "Транзакция выполнена успешно. Комиссия: 0"
        }

        else -> "Транзакция выполнена успешно"
    }
}
