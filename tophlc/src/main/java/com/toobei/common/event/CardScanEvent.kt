package com.toobei.common.event

import com.toobei.common.entity.BankcardRecoData
import com.toobei.common.entity.IdcardRecoData

/**
 * Created by yangLin on 2018/1/7.
 */
data class CardScanEvent(val idcard : IdcardRecoData? = null,val bankCard : BankcardRecoData? = null)