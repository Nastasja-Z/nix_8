package ua.com.alevel.hw_7_data_table_jdbc.view.controller;

import lombok.*;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.PageData;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.ResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ua.com.alevel.hw_7_data_table_jdbc.util.WebRequestUtil.DEFAULT_ORDER_PARAM_VALUE;


public abstract class AbstractController {

    protected void showInfo(Model model, String message) {
        model.addAttribute("message", message);
        showMessage(model, true);
    }

    protected void showInfo(RedirectAttributes redirectAttributes, String message) {
        redirectAttributes.addFlashAttribute("message", message);
    }

    protected void showError(Model model, String message) {
        model.addAttribute("errorMessage", message);
        showMessage(model, true);
    }

    protected void showError(RedirectAttributes redirectAttributes, String error) {
        redirectAttributes.addFlashAttribute("errorMessage", error);
        showMessage(redirectAttributes, true);
    }

    protected void showWarn(Model model, String message) {
        model.addAttribute("warnMessage", message);
        showMessage(model, true);
    }

    protected void showWarn(RedirectAttributes redirectAttributes, String message) {
        redirectAttributes.addFlashAttribute("warnMessage", message);
    }

    protected void showMessage(Model model, boolean show) {
        model.addAttribute("showMessage", show);
    }

    protected void showMessage(RedirectAttributes redirectAttributes, boolean show) {
        redirectAttributes.addFlashAttribute("showMessage", show);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    protected static class HeaderName {

        private String columnName;
        private String tableName;
        private String dbName;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    protected static class HeaderData {

        private String headerName;
        private boolean active;
        private boolean sortable;
        private String sort;
        private String order;
    }

    protected void initDataTable(
            PageData<? extends ResponseDto> response,
            HeaderName[] columnNames,
            Model model) {
        List<HeaderData> headerDataList = new ArrayList<>();
        for (HeaderName headerName : columnNames) {
            HeaderData data = new HeaderData();
            data.setHeaderName(headerName.getColumnName());
            if (StringUtils.isBlank(headerName.getTableName())) {
                data.setSortable(false);
            } else {
                data.setSortable(true);
                data.setSort(headerName.getDbName());
                if (response.getSort().equals(headerName.getDbName())) {
                    data.setActive(true);
                    data.setOrder(response.getOrder());
                } else {
                    data.setActive(false);
                    data.setOrder(DEFAULT_ORDER_PARAM_VALUE);
                }
            }
            headerDataList.add(data);
        }
        model.addAttribute("headerDataList", headerDataList);
        model.addAttribute("pageData", response);
    }

    public ModelAndView findAllRedirect(WebRequest request, ModelMap model, String redirectUrl) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(model::addAttribute);
        }
        return new ModelAndView("redirect:/" + redirectUrl, model);
    }
}
