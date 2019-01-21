package ru.vmaltsev.minirtb.service;

import ru.vmaltsev.minirtb.model.Widget;
import ru.vmaltsev.minirtb.pagination.Page;
import ru.vmaltsev.minirtb.pagination.PageParams;
import ru.vmaltsev.minirtb.range.RangeParams;

import java.util.List;

public interface WidgetService {

    Widget add(Widget widget);

    List<Widget> getAll();

    Widget getById(Long id);

    void delete(Long id);

    Widget update(Widget widget);

    Page<Widget> getPage(PageParams pageParams);

    List<Widget> getByRange(RangeParams rangeParams);


}
