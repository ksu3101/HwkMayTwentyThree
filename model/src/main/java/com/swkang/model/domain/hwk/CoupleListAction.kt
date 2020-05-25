package com.swkang.model.domain.hwk

import com.swkang.model.base.redux.Action
import com.swkang.model.domain.hwk.dto.Couple

sealed class CoupleListAction : Action

/**
 * 100 라인의 과제 파일을 raw 에서 읽어 오는 요청 액션.
 */
object RetrieveFileOneHundredLinesAction: CoupleListAction()

/**
 * 10000 라인의 과제 파일을 raw 에서 읽어 오는 요청 액션.
 */
object RetrieveFileTenThousandLinesAction: CoupleListAction()

/**
 * 500000 라인의 과제 파일을 raw 에서 읽어 오는 요청 액션.
 */
object RetrieveFileFiveHundredThousandLinesAction: CoupleListAction()

/**
 * 과제 파일을 성공적으로 읽어온뒤 Result 액션.
 *
 * @param couples 라인 별 공통 교집항 항목을 Key 로 하여 커플로 묶여진 1개 이상의 항목 목록을 value 로 갖는 `Map`.
 */
data class ReceivedCoupleLinesResultAction(
    val couples: Map<String, List<Couple>>
): CoupleListAction()

/**
 * 커플로 묶여진 아이템 항목들의 상세 목록을 보기 위한 요청 액션.
 *
 * @param detailCouples 커플로 묶여진 1개 이상의 항목의 목록.
 */
data class DetailCoupleListAction(
    val detailCouples: List<Couple>
) : CoupleListAction()