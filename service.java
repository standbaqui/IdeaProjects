package smartsuite.app.bp.dx.srm.ce.analysis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import smartsuite.app.bp.dx.srm.ce.analysis.repository.InsptFactBiRepository;
import smartsuite.app.bp.dx.srm.ce.review.repository.CostReviewRepository;
import smartsuite.app.bp.dx.srm.ce.review.service.CostReviewCalcService;
import smartsuite.app.common.shared.ResultMap;
import smartsuite.data.FloaterStream;

@Service
public class InsptFactBiService {
	@Autowired
	public InsptFactBiRepository insptFactBiRepository;
	
	@Autowired
	public CostReviewRepository costReviewRepository;
	
	@Autowired
    public CostReviewCalcService costReviewCalcService;
	
	//이상징후 점검결과 조회
	public List<Map<String, Object>> findInsptFactList(Map<String, Object> param) {
		return insptFactBiRepository.findInsptFactBiList(param);
	}
	
	//법인코드 조회
	public List<Map<String, Object>> findInsptFactBiCorpCdList(Map<String, Object> param) {
		return insptFactBiRepository.findInsptFactBiCorpCdList(param);
	}
	
	//법인별 이상징후 점검결과 조회
	public List<Map<String, Object>> findInsptFactBiByMonthList(Map<String, Object> param) {
		return insptFactBiRepository.findInsptFactBiCountByMonthList(param);
	}
	//항목별 서머리 조회
	public List<Map<String, Object>> findListByInsptFactCd(Map<String, Object> param) {
		return insptFactBiRepository.findListByInsptFactCd(param);
	}
	
	//항목별 상세 조회
	public Map<String, Object> findDetailListByInsptFactCd(Map<String, Object> param) {
		Map<String, Object> returnMap = new HashMap<>();
		//returnMap.put("resultList1", insptFactBiRepository.findDetailListByInsptFactCd(param)); //그리드 상단 summaryList
		
		List<Map<String, Object>> tmpList = insptFactBiRepository.findInsptFactRsnList(param);
		/** 공통코드 NRGC301 센싱 항목
		 * F001 : SRM 활용율
		 * F002 : 원가편차
		 * F003 : 하위자재 적기 반영
		 * F004 : 원자재가 적기 반영
		 * F005 : Assy 파생코드 S/T
		 * F006 : Assy 동일코드 S/T
		 * F008 : 파생코드 포장비
		 * F009 : 파생코드 운반비
		 * F010 : LOSS
		 * F011 : 고가 원자재
		 * F012 : CIS 불일치
		 * F013 : 단품 파생코드 S/T
		 * F014 : 단품 동일코드 S/T
		 */
		if (("F001").equals(param.get("inspt_fact_cd"))) {
			for (Map<String, Object> map : tmpList) {
				map.put("occr_category", map.get("div_dtl_cont3"));//사유 category code
				map.put("occr_cause_cont", map.get("div_dtl_cont1"));//사유 category text
				map.put("cntr_crn_dt", map.get("div_dtl_cont5"));//계약생성일
				map.put("cntr_no", map.get("div_dtl_cont6"));//계약번호
				map.put("creator", map.get("div_dtl_cont10"));//계약creator
			}
		}
		else if (("F002").equals(param.get("inspt_fact_cd"))) {
			for (Map<String, Object> map : tmpList) {
				map.put("rv_prc", map.get("crtra_val1"));	//표준구매원가
				map.put("infopr_uprc", map.get("crtra_val2"));//Info단가
				map.put("cost_devt_val", map.get("crtra_val3"));//원가편차
				map.put("cost_devt_val_ro", map.get("crtra_val4"));//원가편차율(%)
			}
		}
		else if (("F003").equals(param.get("inspt_fact_cd"))) {
			for (Map<String, Object> map : tmpList) {
				map.put("uprc_rais_lwrn_div_nm", ("U").equals(map.get("div_dtl_cont1"))?"인상":"인하");	//인상/인하
				map.put("rgtt_cost_rv_yn", map.get("div_dtl_cont9"));//적기 경과 후 원가검토 여부
			}
		}
		else if (("F004").equals(param.get("inspt_fact_cd"))) {
			for (Map<String, Object> map : tmpList) {
				map.put("uprc_rais_lwrn_div_nm", ("U").equals(map.get("div_dtl_cont1"))?"인상":"인하");	//인상/인하
				map.put("rawmtl_cost_low_dt", map.get("uprc_appl_dt"));//원자재 단가 인하일(B)
				map.put("rgtt_cost_rv_yn", map.get("div_dtl_cont9"));//적기 경과 후 원가검토 여부
			}
		}
		else if (("F005").equals(param.get("inspt_fact_cd"))) {
			for (Map<String, Object> map : tmpList) {
				map.put("low_st", map.get("crtra_val1"));	//최저S/T
				map.put("st", map.get("crtra_val2"));//S/T
				map.put("crtra_plt_cd", map.get("div_dtl_cont1"));//최저플랜트
				map.put("inspt_crtra_cd", map.get("div_dtl_cont2"));//최저코드
			}
		}
		else if (("F006").equals(param.get("inspt_fact_cd"))) {
			for (Map<String, Object> map : tmpList) {
				map.put("low_st", map.get("crtra_val1"));	//최저S/T
				map.put("st", map.get("crtra_val2"));//S/T
				map.put("crtra_plt_cd", map.get("div_dtl_cont1"));//최저플랜트
				map.put("inspt_crtra_cd", map.get("div_dtl_cont2"));//최저코드
			}
		}
		else if (("F008").equals(param.get("inspt_fact_cd"))) {
			for (Map<String, Object> map : tmpList) {
				map.put("low_pack_expn", map.get("crtra_val1"));	//최저포장비
				map.put("pack_expn", map.get("crtra_val2"));//포장비
				map.put("crtra_plt_cd", map.get("div_dtl_cont1"));//최저플랜트
				map.put("inspt_crtra_cd", map.get("div_dtl_cont2"));//최저코드
			}
		}
		else if (("F009").equals(param.get("inspt_fact_cd"))) {
			for (Map<String, Object> map : tmpList) {
				map.put("low_trpt_expn_ttl_amt", map.get("crtra_val1"));	//최저운반비
				map.put("trpt_expn_ttl_amt", map.get("crtra_val2"));//운반비
				map.put("crtra_plt_cd", map.get("div_dtl_cont1"));//최저플랜트
				map.put("inspt_crtra_cd", map.get("div_dtl_cont2"));//최저코드
			}
		}
		else if (("F010").equals(param.get("inspt_fact_cd"))) {
			for (Map<String, Object> map : tmpList) {
				map.put("rv_prc", map.get("crtra_val1"));	//표준구매원가
				map.put("ag_prc", map.get("crtra_val2"));	//합의가
				map.put("los_ro", map.get("crtra_val3"));//Loss율(%)
			}
		}
		else if (("F011").equals(param.get("inspt_fact_cd"))) { 
			for (Map<String, Object>map : tmpList) { 
				map.put("rawmtl_nm",map.get("div_dtl_cont1"));//원재료명
				map.put("cis_cd", map.get("div_dtl_cont2"));//CIS Code
				map.put("resin_gr", map.get("div_dtl_cont3"));//레진군
				map.put("grd_no", map.get("div_dtl_cont4"));//Grade No.
				map.put("clr", map.get("div_dtl_cont5"));//색상
				map.put("flrt_grd", map.get("div_dtl_cont6"));//난연등급
				map.put("supplier", map.get("div_dtl_cont7"));//공급사
				map.put("lowst_prc_vd_nm", map.get("div_dtl_cont8"));//최저가공급사명
				map.put("crcd", map.get("div_dtl_cont9"));//화폐
				map.put("net_wegt", map.get("crtra_val1"));//Net 중량
				map.put("sr_wegt", map.get("crtra_val2"));//S/R 중량
				map.put("rawmtl_uprc", map.get("crtra_val3"));//원자재 단가
				map.put("lowst_prc", map.get("crtra_val4"));//최저가
			}
		}
		else if (("F012").equals(param.get("inspt_fact_cd"))) {
			for (Map<String, Object> map : tmpList) {
				map.put("cis_1", map.get("div_dtl_cont1"));//최종원가검토날짜
				map.put("cis_2", map.get("div_dtl_cont2"));//cis i/f 날짜
				map.put("appl_grd_cd", map.get("div_dtl_cont3"));//자재등급
				map.put("cat_cls_cd", map.get("div_dtl_cont4"));//c3코드
			}
		}
		else if (("F013").equals(param.get("inspt_fact_cd"))) {
			for (Map<String, Object> map : tmpList) {
				map.put("low_st", map.get("crtra_val1"));	//최저S/T
				map.put("st", map.get("crtra_val2"));//S/T
				map.put("crtra_plt_cd", map.get("div_dtl_cont1"));//최저플랜트
				map.put("inspt_crtra_cd", map.get("div_dtl_cont2"));//최저코드
			}
		}
		else if (("F014").equals(param.get("inspt_fact_cd"))) {
			for (Map<String, Object> map : tmpList) {
				map.put("low_st", map.get("crtra_val1"));	//최저S/T
				map.put("st", map.get("crtra_val2"));//S/T
				map.put("crtra_plt_cd", map.get("div_dtl_cont1"));//최저플랜트
				map.put("inspt_crtra_cd", map.get("div_dtl_cont2"));//최저코드
			}
		}
		
		returnMap.put("resultList2",tmpList);	//그리드 하단 detailList
		
		return returnMap;
	}
	
	//사유대책 관리 조회
	public FloaterStream findRsnCtmList(Map<String, Object> param) {
		return insptFactBiRepository.findRsnCtmList(param);
	}
	
	//과거 DB적용
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> pastDbApplyInsptFactRsn(Map<String, Object> param) {
		List<Map<String, Object>> selected = (List<Map<String, Object>>) param.get("selected");
		String past_yymm = param.get("past_yymm").toString();
		
		List<Map<String, Object>> resultList = new ArrayList<>();
		
		if(selected != null && selected.size() > 0)	{
			for(Map<String,Object> item : selected){
				item.put("past_yymm", past_yymm);
				Map<String,Object> map = insptFactBiRepository.pastDbApplyInsptFactRsn(item);
				resultList.add(map);
			}
		}
		
		return resultList;
	}
	
	//결과전송
	@SuppressWarnings("unchecked")
	public ResultMap saveInsptFactRsn(Map<String, Object> param) {
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) param.get("resultList");
		ResultMap resultMap = ResultMap.getInstance();
		
		if(resultList != null && resultList.size() > 0)	{
			for(Map<String,Object> item : resultList){
				insptFactBiRepository.saveInsptFactRsn(item);
				resultMap.setResultStatus(ResultMap.STATUS.SUCCESS);
			}
		}
		
		return resultMap;
	}

	//가공비 팝업(readOnly) 조회(RGC_COST_EXDO_OPER_INFO)
	public List<Map<String, Object>> insptPopupProcDetails(Map<String, Object> param) {
		List<Map<String, Object>> tmpList = insptFactBiRepository.findListProcDetails(param);
		return tmpList;
	}
}
