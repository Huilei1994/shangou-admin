package com.hl.shangou.service;

import com.hl.shangou.pojo.vo.PermissionVO;
import com.hl.shangou.pojo.vo.RoleVO;
import com.hl.shangou.util.spring.SpringUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * creator：杜夫人
 * date: 2020/6/1
 */
public interface BaseService {
    /**
     * 把角色的集合设置上权限属性
     *
     * @param roles
     * @param permissionVOS
     * @return
     */
    default List<RoleVO> getRoleVOList(List<RoleVO> roles, List<PermissionVO> permissionVOS) {
        Map<Integer, List<PermissionVO>> collect = permissionVOS.stream().collect(Collectors.groupingBy(PermissionVO::getPermissionId));
        for (RoleVO r : roles) {
            String permissions = r.getPermissions();
            if (!StringUtils.isEmpty(permissions)) {
                String[] split = permissions.split(",");
                List<PermissionVO> li = new ArrayList<>();
                for (String s : split) {
                    List<PermissionVO> permissionVOS1 = collect.get(Integer.valueOf(s));
                    if (!CollectionUtils.isEmpty(permissionVOS1)) {
                        PermissionVO p = permissionVOS1.get(0);
                        li.add(p);
                    }
                }

                r.setPermissionVOS(li);
            }
        }
        return roles;
    }

    // 集合变成字符串，逗号分隔
    default String collectionToStr(TreeSet<Integer> collect) {
        if (collect == null) {
            return null;
        }
        // 走到这一步，那么treeSet集合里边就拥有了本身的权限了和传过来的权限都有了
        StringBuffer buffer = new StringBuffer();
        // 把set集合变成字符串，用逗号分隔
        for (Integer pid : collect) {
            buffer.append(pid).append(",");
        }
        if (buffer.length() > 0) {
            buffer.delete(buffer.length() - 1, buffer.length());
        }
        return buffer.toString();
    }

    // 字符串便长城集合，逗号分隔的字符串变成集合
    default TreeSet<Integer> stringToTreeSet(String str) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        if (!StringUtils.isEmpty(str)) {
            String[] split = str.split(",");
            for (String id : split) {
                treeSet.add(Integer.valueOf(id));
            }
        }
        return treeSet;
    }

    // 字符串便长城集合，逗号分隔的字符串变成集合
    default List<String> stringToList(String str) {
        ArrayList<String> objects = new ArrayList<>();
        if (!StringUtils.isEmpty(str)) {
            String[] split = str.split(",");
            for (String id : split) {
                objects.add(id);
            }
        }
        return objects;
    }




    // 删除缓存图片
    default boolean deleteImgCache(Object obj) {
        ImgCacheService bean = SpringUtil.getBean(ImgCacheService.class);// 从IOC容器里边获取实例
        return bean.deleteImgCache(obj);
    }







    /**
     * 从富文本中提取出所有图片路径变成集合
     *
     * @param htmlStr
     * @return
     */
    default List<String> getImgStrToList(String htmlStr) {
        List<String> list = new ArrayList<>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        // String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                list.add(m.group(1));
            }
        }
        return list;
    }





}
