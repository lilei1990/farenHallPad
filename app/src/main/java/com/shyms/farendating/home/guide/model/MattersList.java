package com.shyms.farendating.home.guide.model;

import java.util.List;

/**
 * Created by hks on 2016/3/17.
 */
public class MattersList {


    /**
     * code : 0
     * data : ["网上在线填写后打印的对外劳务合作经营资格申请表|收原件|3份|0|0","企业法人营业执照|验原件收复印件|3份|0|0","银行资信证明|验原件收复印件|3份|0|0","会计师事务所出具的企业验资报告、财务年度报告、资产负债表|验原件收复印件|3份|0|0","税务机关出具的完税证明|验原件收复印件|3份|0|0","经营场所产权证明或固定场所租赁证明|验原件收复印件|3份|0|0",".公司章程|收原件|3份|0|0","经营管理制度|收原件|3份|0|0","ISO9000质量管理体系认证证书|收复印件|3份|0|0",".相关专业人员证书（如对外劳务技能证书）|收复印件|3份|0|0","拟开展对外劳务合作的国别及地区可行性报告|收原件|3份|0|0","具有对外劳务合作经营资格的企业出具的提供外派劳务人数证明|收原件|3份|0|0","外商投资企业批准证书|验原件收复印件|3份|0|0","外商投资企业营业执照|验原件收复印件|3份|0|0"]
     * message : null
     * meta : null
     */

    private String code;
    private Object message;
    private Object meta;
    private List<String> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getMeta() {
        return meta;
    }

    public void setMeta(Object meta) {
        this.meta = meta;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MattersList{" +
                "code='" + code + '\'' +
                ", message=" + message +
                ", meta=" + meta +
                ", data=" + data +
                '}';
    }
}
