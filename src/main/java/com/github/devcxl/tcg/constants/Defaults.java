package com.github.devcxl.tcg.constants;

import com.github.devcxl.tcg.model.CodeGroup;
import com.github.devcxl.tcg.model.CodeRoot;
import com.github.devcxl.tcg.model.CodeTemplate;
import com.github.devcxl.tcg.utils.StringUtils;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.util.io.FileUtil;
import org.apache.commons.lang.time.DateFormatUtils;

import javax.swing.*;
import java.util.*;

/**
 * 内置参数
 *
 * @author hehaiyangwork@gmail.com
 * @date 2017/4/16
 */
public class Defaults {
    private static final Logger LOGGER = Logger.getInstance(Defaults.class);

    /**
     * 项目图片
     */
    public static final Icon CODEGEN = IconLoader.findIcon("/icons/codegen.png");

    public static Map<String, String> getDefaultVariables() {
        Map<String, String> context = new HashMap<>(7);
        Calendar calendar = Calendar.getInstance();
        context.put("YEAR", String.valueOf(calendar.get(Calendar.YEAR)));
        context.put("MONTH", String.valueOf(calendar.get(Calendar.MONTH) + 1));
        context.put("DAY", String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        context.put("DATE", DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd"));
        context.put("TIME", DateFormatUtils.format(calendar.getTime(), "HH:mm:ss"));
        context.put("NOW", DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
        context.put("USER", System.getProperty("user.name"));
        return context;
    }

    /**
     * 获取默认的模板
     */
    public static List<CodeRoot> getDefaultTemplates() {
        // 设置默认分组和模板
        List<CodeGroup> groups = new ArrayList<>();
        try {
            // models
            List<CodeTemplate> models = new ArrayList<>();
            CodeTemplate modelCodeTemplate = new CodeTemplate(UUID.randomUUID().toString(),
                    "Model", "java", "${model}",
                    FileUtil.loadTextAndClose(
                            Defaults.class.getResourceAsStream("/templates/ModelTemplate.vm")
                    ),
                    "model", false);
            models.add(modelCodeTemplate);
            CodeGroup modelGroup = new CodeGroup(UUID.randomUUID().toString(), "model", 1, models);
            groups.add(modelGroup);

            // service
            List<CodeTemplate> service = new ArrayList<>();
            CodeTemplate serviceCodeTemplate = new CodeTemplate(UUID.randomUUID().toString(),
                    "Service", "java", "${model}Service",
                    FileUtil.loadTextAndClose(
                            Defaults.class.getResourceAsStream("/templates/ServiceTemplate.vm")
                    ),
                    "service", false);
            service.add(serviceCodeTemplate);
            CodeGroup serviceGroup = new CodeGroup(UUID.randomUUID().toString(), "service", 2, service);
            groups.add(serviceGroup);

            // serviceImpl
            List<CodeTemplate> serviceImpl = new ArrayList<>();
            CodeTemplate serviceImplCodeTemplate = new CodeTemplate(UUID.randomUUID().toString(),
                    "Service",
                    "java",
                    "${model}ServiceImpl",
                    FileUtil.loadTextAndClose(
                            Defaults.class.getResourceAsStream("/templates/ServiceImplTemplate.vm")
                    ),
                    "service", false);
            serviceImpl.add(serviceImplCodeTemplate);
            CodeGroup serviceImplGroup = new CodeGroup(UUID.randomUUID().toString(), "serviceImpl", 3, serviceImpl);
            groups.add(serviceImplGroup);


            // dao层
            List<CodeTemplate> daos = new ArrayList<>();
            CodeTemplate daoCodeTemplate = new CodeTemplate(UUID.randomUUID().toString(),
                    "Dao",
                    "java",
                    "${model}Dao",
                    FileUtil.loadTextAndClose(
                            Defaults.class.getResourceAsStream("/templates/DaoTemplate.vm")
                    ), "dao", false);

            daos.add(daoCodeTemplate);
            CodeTemplate mapperCodeTemplate = new CodeTemplate(UUID.randomUUID().toString(),
                    "Mapper",
                    "xml",
                    "${model}Mapper",
                    FileUtil.loadTextAndClose(
                            Defaults.class.getResourceAsStream("/templates/MapperTemplate.vm")
                    ),
                    "mapper", true);
            daos.add(mapperCodeTemplate);
            CodeGroup implGroup = new CodeGroup(UUID.randomUUID().toString(), "dao", 4, daos);
            groups.add(implGroup);

            // Controller
            List<CodeTemplate> apis = new ArrayList<>();
            CodeTemplate apiCodeTemplate = new CodeTemplate(UUID.randomUUID().toString(),
                    "Controller",
                    "java",
                    "${model}Controller",
                    FileUtil.loadTextAndClose(
                            Defaults.class.getResourceAsStream("/templates/ControllerTemplate.vm")
                    ),
                    "controller", false);
            apis.add(apiCodeTemplate);
            CodeGroup apiGroup = new CodeGroup(UUID.randomUUID().toString(), "api", 5, apis);
            groups.add(apiGroup);

            // page分页工具
            List<CodeTemplate> pages = new ArrayList<>();
            CodeTemplate pageCodeTemplate = new CodeTemplate(UUID.randomUUID().toString(),
                    "Page",
                    "java",
                    "Page",
                    FileUtil.loadTextAndClose(
                            Defaults.class.getResourceAsStream("/templates/PageTemplate.vm")
                    ),
                    "util", false);
            pages.add(pageCodeTemplate);
            CodeTemplate pageParamCodeTemplate = new CodeTemplate(UUID.randomUUID().toString(),
                    "PageParam",
                    "java",
                    "PageParam",
                    FileUtil.loadTextAndClose(
                            Defaults.class.getResourceAsStream("/templates/PageParamTemplate.vm")
                    ),
                    "util", false);
            pages.add(pageParamCodeTemplate);
            CodeGroup pagesGroup = new CodeGroup(UUID.randomUUID().toString(), "page", 6, pages);
            groups.add(pagesGroup);

        } catch (Exception e) {
            LOGGER.error(StringUtils.getStackTraceAsString(e));
        }
        // 设置默认根, 名称为root
        List<CodeRoot> roots = new ArrayList<>();
        roots.add(new CodeRoot(UUID.randomUUID().toString(), "example", groups));
        return roots;
    }

}
