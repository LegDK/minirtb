package ru.vmaltsev.minirtb.repo;

import org.springframework.stereotype.Repository;
import ru.vmaltsev.minirtb.exception.WidgetNotFoundException;
import ru.vmaltsev.minirtb.model.Widget;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class WidgetRepoImpl implements WidgetRepo {

    private ConcurrentHashMap<Long, Widget> widgets = new ConcurrentHashMap<>();

    @Override
    public Widget add(Widget widget, Long newId) {
        widgets.put(newId, widget);
        return widget;
    }

    @Override
    public List<Widget> getAll() {
        return new ArrayList<>(widgets.values());
    }

    @Override
    public Widget getById(Long id) {
        if (checkWidgetId(id)) {
            return widgets.get(id);
        } else {
            throw new WidgetNotFoundException();
        }
    }

    @Override
    public void delete(Long id) {
        if (checkWidgetId(id)) {
            widgets.remove(id);
        } else {
            throw new WidgetNotFoundException();
        }
    }

    @Override
    public Widget update(Widget widget) {
        if (checkWidgetId(widget.getId())) {
            widgets.replace(widget.getId(), widget);
            return widget;
        } else {
            throw new WidgetNotFoundException();
        }
    }

    private boolean checkWidgetId(Long id) {
        return widgets.containsKey(id);
    }

}

