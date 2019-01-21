package ru.vmaltsev.minirtb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vmaltsev.minirtb.model.Widget;
import ru.vmaltsev.minirtb.pagination.Page;
import ru.vmaltsev.minirtb.pagination.PageParams;
import ru.vmaltsev.minirtb.range.RangeParams;
import ru.vmaltsev.minirtb.service.WidgetService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WidgetController {

    private final WidgetService widgetService;

    @Autowired
    public WidgetController(WidgetService widgetService) {
        this.widgetService = widgetService;
    }

    @GetMapping("/all")
    public List<Widget> getWidgets() {
        return widgetService.getAll();
    }

    @GetMapping("/{id:\\d+}")
    public Widget getWidgetById(@PathVariable("id") Long id) {
        return widgetService.getById(id);
    }

    //усложнение 1
    @GetMapping("/getPage")
    public Page<Widget> getPage(PageParams pageParams) {
        return widgetService.getPage(pageParams);
    }

    //усложнение 2
    @GetMapping("/getByRange")
    public List<Widget> getByRange(RangeParams rangeParams) {
        return widgetService.getByRange(rangeParams);
    }

    @PostMapping("/add")
    public Widget addWidget(@RequestBody Widget widget) {
        return widgetService.add(widget);
    }

    @PostMapping("/update")
    public Widget updateWidget(@RequestBody Widget widget) {
        return widgetService.update(widget);
    }

    @DeleteMapping("/delete/{id:\\d+}")
    public void deleteWidget(@PathVariable("id") Long id) {
        widgetService.delete(id);
    }
}
