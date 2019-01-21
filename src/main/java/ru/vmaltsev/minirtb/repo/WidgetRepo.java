package ru.vmaltsev.minirtb.repo;

import ru.vmaltsev.minirtb.model.Widget;

import java.util.List;

public interface WidgetRepo {

    Widget add(Widget widget, Long newId);

    List<Widget> getAll();

    Widget getById(Long id);

    void delete(Long id);

    Widget update(Widget widget);
}
