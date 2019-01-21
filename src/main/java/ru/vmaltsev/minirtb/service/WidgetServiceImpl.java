package ru.vmaltsev.minirtb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vmaltsev.minirtb.model.Widget;
import ru.vmaltsev.minirtb.pagination.Page;
import ru.vmaltsev.minirtb.pagination.PageParams;
import ru.vmaltsev.minirtb.range.RangeParams;
import ru.vmaltsev.minirtb.repo.WidgetRepo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class WidgetServiceImpl implements WidgetService {

    private final WidgetRepo widgetRepo;

    @Autowired
    public WidgetServiceImpl(WidgetRepo widgetRepo) {
        this.widgetRepo = widgetRepo;
    }

    private AtomicLong id = new AtomicLong();

    @Override
    public Widget add(Widget widget) {
        Long newId = id.getAndIncrement();
        ;
        widget.setId(newId);
        if (widget.getZIndex() == null) {
            widget.setZIndex(getMaxZIndex());
        } else {
            updateZIndex(widget.getZIndex());
        }
        return widgetRepo.add(widget, newId);
    }

    @Override
    public List<Widget> getAll() {
        return widgetRepo.getAll().stream()
                .sorted(Comparator.comparingLong(Widget::getZIndex)).collect(Collectors.toList());
    }

    @Override
    public Widget getById(Long id) {
        return widgetRepo.getById(id);
    }

    @Override
    public void delete(Long id) {
        widgetRepo.delete(id);
    }

    @Override
    public Widget update(Widget widget) {
        return widgetRepo.update(widget);
    }

    //усложнение 1
    @Override
    public Page<Widget> getPage(PageParams pageParams) {
        List<Widget> list = new ArrayList<>(getAll());
        List<Widget> newList = new ArrayList<>();

        Long start = (pageParams.getPage() - 1) * pageParams.getSize();
        Long end = pageParams.getPage() * pageParams.getSize();
        if (start < 0) {
            start = 0L;
        }
        if (end > list.size()) {
            end = (long) list.size();
        }
        for (Long i = start; i < end; i++) {
            newList.add(list.get(Math.toIntExact(i)));
        }
        return new Page<>(newList,
                (long) Math.ceil((double) widgetRepo.getAll().size() / (double) pageParams.getSize()),
                (long) widgetRepo.getAll().size(),
                pageParams.getPage(),
                pageParams.getSize());
    }

    //усложнение 2
    @Override
    public List<Widget> getByRange(RangeParams rangeParams) {
        List<Widget> newList = new ArrayList<>();
        widgetRepo.getAll().forEach(widget -> {
            Double x1 = widget.getX() - (widget.getWidth() / 2);
            Double x2 = widget.getX() + (widget.getWidth() / 2);
            Double y1 = widget.getY() - (widget.getHeight() / 2);
            Double y2 = widget.getY() + (widget.getHeight() / 2);
            if (rangeParams.getX1() <= x1 && rangeParams.getX2() >= x2
                    && rangeParams.getY1() <= y1 && rangeParams.getY2() >= y2) {
                newList.add(widget);
            }
        });
        return newList;
    }

    private Long getMaxZIndex() {
        return widgetRepo.getAll().stream()
                .max(Comparator.comparingLong(Widget::getZIndex))
                .map(Widget::getZIndex).orElse(0L) + 1;
    }

    private void updateZIndex(Long zIndex) {
        widgetRepo.getAll().stream()
                .filter(widget -> widget.getZIndex() >= zIndex)
                .forEach(widget -> widget.setZIndex(widget.getZIndex() + 1));
    }
}
