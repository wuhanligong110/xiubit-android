package com.toobei.common.event

import com.toobei.common.entity.BankcardRecoData
import com.toobei.common.entity.IdcardRecoData
import java.io.File

/**
 * Created by yangLin on 2018/1/7.
 */
data class UpCardScanDataEvent(val file : File, val scanType : Int )