package com.ctb.resources.mapper.biz;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ctb.commons.entity.PrescriptionRecord;
import com.ctb.data.persistence.mapper.CtbMapper;

@Repository
public interface PrescriptionRecordMapper extends CtbMapper<PrescriptionRecord> {
    
    public int savePrescriptionRecord(PrescriptionRecord prescriptionRecord);

    public PrescriptionRecord findPrescriptionByParams(Map<String, String> params);
    
    /**
     * 
     * @Title: updatePrescriptionRecord
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author ckm
     * @date 2019年4月19日 下午2:46:19
     * @param prescriptionRecord
     * @return
     */
    public int updatePrescriptionRecord(PrescriptionRecord prescriptionRecord);
    
    public List<PrescriptionRecord> findListByParams(Map<Object, Object> param);
    
    public List<Long> findRangeByParams(Map<Object, Object> param);
    
    public Long findOffsetByParams(Map<Object, Object> param);
    
    public PrescriptionRecord findPrescriptionByOrderNo(Map<String, String> params);
    
    public List <PrescriptionRecord> findPrescriptionByOpenId(Map<String, String> params);
    
    public int total(Map map);
}