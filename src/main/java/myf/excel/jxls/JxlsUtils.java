package myf.excel.jxls;

import lombok.extern.java.Log;
import myf.entity.DeptInfo;
import myf.entity.User;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JXLS 场景复杂特质模板
 * 通过KEY 去绑定映射
 * 底层封装POI
 */
@Log
public class JxlsUtils {
    public static void main(String[] asd) throws IOException {
        log.info("start");
        List<String> sheetNames = new ArrayList<>();
        sheetNames.add("template-1");
        sheetNames.add("template-2");
        JxlsUtils request =  new JxlsUtils();

        exceportExcel("src/main/resources/", "E://", "jxls-Template.xlsx", getData(), sheetNames);
         log.info("Jxls Export success");
    }





    /**
     * 设置JXLS
     *
     * @param filePath
     * @param fileName
     * @param outPath
     * @param data
     * @param sheetNames
     * @throws IOException
     */
    public static void exceportExcel(String filePath, String outPath, String fileName, List data, List<String> sheetNames) throws IOException {
        //设置jxls相关信息
        log.info(filePath + fileName);
        InputStream in = new FileInputStream(filePath + fileName);
        OutputStream out = new FileOutputStream(outPath + "123.xlsx");

        Context context = new Context();
        context.putVar("resultData", data);
        context.putVar("sheetNames", sheetNames);
        JxlsHelper.getInstance().processTemplate(in, out, context);

    }


    public static void exportExcel(InputStream is, OutputStream os, DeptInfo data) throws IOException{
        Context context = PoiTransformer.createInitialContext();

                context.putVar("resultData", data);

        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        Transformer transformer  = jxlsHelper.createTransformer(is, os);
        //获得配置
        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator)transformer.getTransformationConfig().getExpressionEvaluator();
        //设置静默模式，不报警告
        //evaluator.getJexlEngine().setSilent(true);
        //函数强制，自定义功能
//        Map<String, Object> funcs = new HashMap<String, Object>();
//        funcs.put("utils", new JxlsUtils());    //添加自定义功能
//        evaluator.getJexlEngine().SETsetFunctions(funcs);
        //必须要这个，否者表格函数统计会错乱
        jxlsHelper.setUseFastFormulaProcessor(false).processTemplate(context, transformer);
    }




    public static List<DeptInfo> getData() {
        List<DeptInfo> data = new ArrayList<>();
        List<User> users = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            User user = new User();
            user.setIndex(String.valueOf(i + 1));
            user.setName("张三" + i);
            user.setAge(String.valueOf(i));
            user.setSex((i % 2) == 0 ? "男" : "女");
            user.setDiss("上海市浦东新区");
            users.add(user);
        }
        DeptInfo deptInfo = new DeptInfo();
        deptInfo.setTheCompany("中行");
        deptInfo.setDeptName("IT");

            List<String> dates = new ArrayList<>();
            dates.add("07-01");
            dates.add("07-02");
        deptInfo.setDates(dates);
        deptInfo.setDeptName("IT");
        deptInfo.setUsers(users);
        DeptInfo deptInfo1 = new DeptInfo();

        BeanUtils.copyProperties(deptInfo, deptInfo1);
        deptInfo.setTheCompany("工行");
        deptInfo.setDeptName("BDP");
        data.add(deptInfo);
        data.add(deptInfo1);
        return data;
    }
}
