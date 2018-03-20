package cn.xiaoyaoji.plugin.history;

import cn.xiaoyaoji.core.common.DocType;
import cn.xiaoyaoji.data.DataFactory;
import cn.xiaoyaoji.data.bean.Doc;
import cn.xiaoyaoji.event.DocUpdatedEvent;
import cn.xiaoyaoji.listener.ApplicationListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @author zhoujingjie
 * Date 2018-03-19
 */
public class DocUpdatedListener implements ApplicationListener<DocUpdatedEvent> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onEvent(DocUpdatedEvent event) {
        String comment = event.getComment();
        Doc before = event.getBefore();
        Doc now = event.getNow();

        if (org.apache.commons.lang3.StringUtils.isBlank(comment)) {
            comment = compareDocUpdateRecord(before, now);
            if (comment.length() == 0) {
                comment = "修改文档";
            }
        }
        DocHistory history = new DocHistory();
        BeanUtils.copyProperties(before, history);
        history.setName(now.getName());
        history.setComment(comment);
        history.setUserId(event.getOperator().getId());
        history.setCreateTime(new Date());
        history.setDocId(now.getId());
        DataFactory.instance().insert(history);
    }

    private String compareDocUpdateRecord(Doc old, Doc now) {
        StringBuilder sb = new StringBuilder();
        if (compareModify(old.getName(), now.getName())) {
            sb.append("文档名称,");
        }
        if (DocType.SYS_HTTP.getTypeName().equals(old.getType())) {
            if (old.getContent() == null) {
                if (now.getContent() != null)
                    sb.append("文档内容,");
            } else if (now.getContent() != null) {

                JSONObject oldObj = JSON.parseObject(old.getContent());
                JSONObject newObj = JSON.parseObject(now.getContent());
                if (compareModify(oldObj.getString("requestMethod"), newObj.getString("requestMethod"))) {
                    sb.append("请求方法,");
                }
                if (compareModify(oldObj.getString("dataType"), newObj.getString("dataType"))) {
                    sb.append("数据类型,");
                }
                if (compareModify(oldObj.getString("contentType"), newObj.getString("contentType"))) {
                    sb.append("响应类型,");
                }
                if (compareModify(oldObj.getString("status"), newObj.getString("status"))) {
                    sb.append("状态,");
                }
                if (compareModify(oldObj.getString("ignoreGHttpReqArgs"), newObj.getString("ignoreGHttpReqArgs"))) {
                    sb.append("忽略全局请求参数,");
                }
                if (compareModify(oldObj.getString("ignoreGHttpReqHeaders"), newObj.getString("ignoreGHttpReqHeaders"))) {
                    sb.append("忽略全局请求头,");
                }
                if (compareModify(oldObj.getString("ignoreGHttpRespHeaders"), newObj.getString("ignoreGHttpRespHeaders"))) {
                    sb.append("忽略全局响应头,");
                }
                if (compareModify(oldObj.getString("ignoreGHttpRespArgs"), newObj.getString("ignoreGHttpRespArgs"))) {
                    sb.append("忽略全局响应参数,");
                }
                if (compareModify(oldObj.getString("description"), newObj.getString("description"))) {
                    sb.append("接口描述,");
                }
                if (compareModify(oldObj.getString("requestArgs"), newObj.getString("requestArgs"))) {
                    sb.append("请求参数,");
                }
                if (compareModify(oldObj.getString("requestHeaders"), newObj.getString("requestHeaders"))) {
                    sb.append("请求头,");
                }
                if (compareModify(oldObj.getString("responseHeaders"), newObj.getString("responseHeaders"))) {
                    sb.append("响应头,");
                }
                if (compareModify(oldObj.getString("responseArgs"), newObj.getString("responseArgs"))) {
                    sb.append("响应数据,");
                }
                if (compareModify(oldObj.getString("example"), newObj.getString("example"))) {
                    sb.append("示例数据,");
                }
            }
        }
        if (sb.length() > 0) {
            sb = sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

    private boolean compareModify(String old, String now) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(now) && !now.equals(old)) {
            return true;
        }
        return false;
    }
}
