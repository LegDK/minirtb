package ru.vmaltsev.minirtb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vmaltsev.minirtb.model.Widget;
import ru.vmaltsev.minirtb.pagination.Page;
import ru.vmaltsev.minirtb.pagination.PageParams;
import ru.vmaltsev.minirtb.range.RangeParams;
import ru.vmaltsev.minirtb.repo.WidgetRepo;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class WidgetServiceImpl implements WidgetService {

    private final WidgetRepo widgetRepo;

    private AtomicLong id = new AtomicLong();

    @Autowired
    public WidgetServiceImpl(WidgetRepo widgetRepo) {
        this.widgetRepo = widgetRepo;
    }

    @Override
    public Widget add(Widget widget) {
        Long newId = id.getAndIncrement();
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
                .sorted(Comparator.comparingLong(Widget::getZIndex))
                .collect(Collectors.toList());
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
        Long pageIndex = pageParams.getPage() - 1 < 0 ? 0 : pageParams.getPage() - 1;

        Long start = pageIndex * pageParams.getSize();

        return new Page<>(getAll().stream()
                .skip(start)
                .limit(pageParams.getSize())
                .collect(Collectors.toList()),
                pageParams.getSize(),
                (long) widgetRepo.getAll().size(),
                pageIndex + 1,
                pageParams.getSize());
    }

    //усложнение 2
    @Override
    public List<Widget> getByRange(RangeParams rangeParams) {
        return getAll().stream().filter(widget ->
                        (rangeParams.getX1() <= widget.getX() - (widget.getWidth() / 2)) &&
                        (rangeParams.getX2() >= widget.getX() + (widget.getWidth() / 2)) &&
                        (rangeParams.getY1() <= widget.getY() - (widget.getHeight() / 2)) &&
                        (rangeParams.getY2() >= widget.getY() + (widget.getHeight() / 2))
        ).collect(Collectors.toList());
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
