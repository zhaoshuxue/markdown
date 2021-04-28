package com.zsx.md.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zsx.md.config.PropertiesConfig;
import com.zsx.md.dao.MnoteDao;
import com.zsx.md.entity.Mnote;
import com.zsx.md.service.NoteService;
import com.zsx.md.utils.FileUtil;
import com.zsx.md.utils.TreeUtil;
import com.zsx.md.vo.NoteVO;
import com.zsx.md.vo.ResultData;
import com.zsx.md.vo.TreeNode;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class NoteServiceImpl implements NoteService {

    private static Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

    @Resource
    private MnoteDao mnoteDao;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Override
    public List<TreeNode> getNoteListByUserId(Integer userId) {

        List<Mnote> list = mnoteDao.findByStatusAndUserId(0, userId);
        logger.info("数据库中查询结果：{}", JSON.toJSONString(list));
        if (CollectionUtils.isEmpty(list)) {
            initNote(userId);
            return getNoteListByUserId(userId);
        }

        List<TreeNode> treeNodes = TreeUtil.convert(list);

        List<TreeNode> tree = TreeUtil.buildTree(treeNodes);

        tree = TreeUtil.sortTree(tree);

        logger.info("得到最终的tree对象：{}", JSON.toJSONString(tree));

        return tree;
    }

    private void initNote(Integer userId) {
        Mnote note = new Mnote();

        note.setPid(0);
        note.setUserId(userId);
        note.setTypes(1);
        note.setTitle("我的文档");
        note.setSummary("");
        note.setContent("");
        note.setOrders(0);
        note.setStatus(0);
        note.setCreatePerson("system");
        note.setCreatedTime(new Date());
        note.setUpdatedTime(new Date());

        Mnote save = mnoteDao.save(note);
        logger.info("save = {}", JSON.toJSONString(save));
    }

    @Override
    public ResultData<NoteVO> getNote(Integer id, boolean getContent) {
        ResultData<NoteVO> resultData = new ResultData<>();

        Optional<Mnote> mnoteOptional = mnoteDao.findById(id);
        if (mnoteOptional.isPresent()) {
            Mnote mnote = mnoteOptional.get();

            NoteVO noteVO = JSONObject.parseObject(JSON.toJSONString(mnote), NoteVO.class);

            if (getContent) {
                String content = noteVO.getContent();

                String text = FileUtil.readFile(propertiesConfig.getMdFilePath() + content);

                noteVO.setText(text);
            }

            resultData.setSuccess(true);
            resultData.setData(noteVO);
        }
        return resultData;
    }

    @Override
    public ResultData<String> saveNote(NoteVO noteVO) {

        String content = String.valueOf(System.currentTimeMillis()) + ".md";
//        FileUtil.checkFileExist(propertiesConfig.getMdFilePath() + "note-");
        FileUtil.writeFile(noteVO.getText(), propertiesConfig.getMdFilePath() + content);

//        todo
        noteVO.setTypes(1);
        noteVO.setSummary("");
        noteVO.setContent(content);
        noteVO.setOrders(new Random().nextInt(100));
        noteVO.setStatus(0);
        noteVO.setCreatePerson("admin");

        Mnote mnote = JSONObject.parseObject(JSON.toJSONString(noteVO), Mnote.class);

        Mnote save = mnoteDao.save(mnote);
        logger.info("save = {}", JSON.toJSONString(save));

//        sql = "select id from m_note where pid = ? and content = ? and orders = ?";
//
//        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, noteVO.getPid(), content, noteVO.getOrders());
//        Map<String, Object> map = list.get(0);

//        return ResultData.build(true, "保存成功", map.get("id"));
        return ResultData.build(true, "保存成功", save.getId());
    }

    @Override
    public ResultData<String> updateNote(NoteVO noteVO) {
        ResultData<NoteVO> resultData = this.getNote(noteVO.getId(), false);
        Integer id = null;
        if (resultData.isSuccess()) {
            NoteVO data = resultData.getData();
            id = data.getId();

            String content = data.getContent();
            FileUtil.writeFile(noteVO.getText(), propertiesConfig.getMdFilePath() + content);

            Mnote mnote = mnoteDao.findById(id).get();
            mnote.setTitle(noteVO.getTitle());
            mnote.setContent(content);
            mnote.setUpdatePerson(noteVO.getUpdatePerson());
            mnote.setUpdatedTime(new Date());

            mnoteDao.save(mnote);
            logger.info("update = {}", JSON.toJSONString(mnote));

        } else {
            return this.saveNote(noteVO);
        }
        return ResultData.build(true, "保存成功", id);
    }


    /**
     * 移动
     *
     * @param id     被移动的节点id
     * @param target 目标节点id
     * @param type   移动类型， 1：成为目标子节点，2：成为前节点，3：成为后节点
     * @return
     */
    @Override
    @Transactional
    public ResultData<String> moveNode(Integer id, Integer target, Integer type) {
        ResultData<String> resultData = new ResultData<>();
        if (id == null || target == null || type == null) {
            resultData.setSuccess(false);
            resultData.setMessage("参数错误");
            return resultData;
        }

        ResultData<NoteVO> noteResultData = this.getNote(id, false);
        NoteVO note = noteResultData.getData();

        if (type == 1) {
            // 子节点，直接修改pid即可
//            String sql = "UPDATE m_note SET pid=?, orders=?, update_person=? WHERE (id= ?)";
//            int update = jdbcTemplate.update(sql, target, 1, note.getUpdatePerson(), note.getId());

            Mnote mnote = mnoteDao.findById(note.getId()).get();
            mnote.setPid(target);
            mnote.setOrders(1);
            mnote.setUpdatePerson(note.getUpdatePerson());
            mnote.setUpdatedTime(new Date());

            mnoteDao.save(mnote);

        } else if (type == 2) {
            // 成为前节点
            ResultData<NoteVO> targetResultData = this.getNote(target, false);
            NoteVO targetNode = targetResultData.getData();
            // 让目标及下面的节点的排序orders全部+1
//            String sql = "UPDATE m_note SET orders=orders+1, update_person=? WHERE (pid= ? and orders >= ?)";
//            jdbcTemplate.update(sql, note.getUpdatePerson(), targetNode.getPid(), targetNode.getOrders());
            mnoteDao.updateOrders1(targetNode.getPid(), targetNode.getOrders());


            // 接着 把 节点 替换 目标节点 的位置
//            sql = "UPDATE m_note SET pid=?, orders=?, update_person=? WHERE (id= ?)";
//            jdbcTemplate.update(sql, targetNode.getPid(), targetNode.getOrders(), note.getUpdatePerson(), note.getId());

            mnoteDao.updateOrdersById(targetNode.getPid(), targetNode.getOrders(), note.getId());

//            Mnote mnote = mnoteDao.findById(note.getId()).get();
//            mnote.setPid(targetNode.getPid());
//            mnote.setOrders(targetNode.getOrders());
//
//            mnoteDao.save(mnote);

        } else if (type == 3) {
            // 成为后节点
            ResultData<NoteVO> targetResultData = this.getNote(target, false);
            NoteVO targetNode = targetResultData.getData();

            // 让目标下面的节点的排序orders全部+1
//            String sql = "UPDATE m_note SET orders=orders+1, update_person=? WHERE (pid= ? and orders > ?)";
//            jdbcTemplate.update(sql, note.getUpdatePerson(), targetNode.getPid(), targetNode.getOrders());
            mnoteDao.updateOrders2(targetNode.getPid(), targetNode.getOrders());

            // 接着 把 节点 放到 目标节点 的排序下面的位置
//            sql = "UPDATE m_note SET pid=?, orders=?, update_person=? WHERE (id= ?)";
//            jdbcTemplate.update(sql, targetNode.getPid(), targetNode.getOrders() + 1, note.getUpdatePerson(), note.getId());
            mnoteDao.updateOrdersById(targetNode.getPid(), targetNode.getOrders() + 1, note.getId());

        } else {
            resultData.setSuccess(false);
            resultData.setMessage("参数type错误");
        }

        return resultData;
    }
}
