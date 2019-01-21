package ru.vmaltsev.minirtb.service;

import org.junit.Before;
import org.junit.Test;
import ru.vmaltsev.minirtb.exception.WidgetNotFoundException;
import ru.vmaltsev.minirtb.model.Widget;
import ru.vmaltsev.minirtb.pagination.PageParams;
import ru.vmaltsev.minirtb.range.RangeParams;
import ru.vmaltsev.minirtb.repo.WidgetRepo;
import ru.vmaltsev.minirtb.repo.WidgetRepoImpl;

import static org.junit.Assert.assertEquals;

public class WidgetServiceImplTest {

    private WidgetRepo widgetRepo;

    private WidgetServiceImpl widgetService;

    @Before
    public void init() {
        widgetRepo = new WidgetRepoImpl();
        widgetService = new WidgetServiceImpl(widgetRepo);
    }

    @Test
    public void testAddOneItemWithZIndex() {
        Widget ex = new Widget();
        ex.setX(100.0);
        ex.setY(100.0);
        ex.setHeight(100.0);
        ex.setWidth(100.0);
        ex.setZIndex(100L);

        Widget actual = widgetService.add(ex);

        assertEquals(actual.getX(), ex.getX());
        assertEquals(actual.getY(), ex.getY());
        assertEquals(actual.getWidth(), ex.getWidth());
        assertEquals(actual.getHeight(), ex.getHeight());
        assertEquals(actual.getZIndex(), ex.getZIndex());
    }

    @Test
    public void testAddOneItemWithoutZIndex() {
        Widget ex = new Widget();
        ex.setX(100.0);
        ex.setY(100.0);
        ex.setHeight(100.0);
        ex.setWidth(100.0);

        assertEquals(widgetService.getAll().size(), 0);

        Widget actual = widgetService.add(ex);

        assertEquals(1, (long) actual.getZIndex());
    }

    @Test
    public void testMaxZIndex() {
        Widget widgetWithZIndex = new Widget();
        widgetWithZIndex.setX(100.0);
        widgetWithZIndex.setY(100.0);
        widgetWithZIndex.setHeight(100.0);
        widgetWithZIndex.setWidth(100.0);
        widgetWithZIndex.setZIndex(100L);

        widgetService.add(widgetWithZIndex);

        Widget ex = new Widget();
        ex.setX(100.0);
        ex.setY(100.0);
        ex.setHeight(100.0);
        ex.setWidth(100.0);

        Widget actual = widgetService.add(ex);

        assertEquals(101L, (long) actual.getZIndex());
    }

    @Test
    public void testBumpingZIndex() {
        Widget first = new Widget();
        first.setX(100.0);
        first.setY(100.0);
        first.setHeight(100.0);
        first.setWidth(100.0);
        first.setZIndex(100L);

        Widget second = new Widget();
        second.setX(100.0);
        second.setY(100.0);
        second.setHeight(100.0);
        second.setWidth(100.0);
        second.setZIndex(120L);

        Widget ex = new Widget();
        ex.setX(100.0);
        ex.setY(100.0);
        ex.setHeight(100.0);
        ex.setWidth(100.0);
        ex.setZIndex(100L);


        widgetService.add(first);

        widgetService.add(second);

        widgetService.add(ex);

        Widget actualFirst = widgetService.getById(first.getId());

        Widget actualSecond = widgetService.getById(second.getId());

        assertEquals(101L, (long) actualFirst.getZIndex());

        assertEquals(121L, (long) actualSecond.getZIndex());
    }

    @Test(expected = WidgetNotFoundException.class)
    public void testGetByNonExistId() {
        widgetService.getById(9999L);
    }

    @Test
    public void testUpdate() {
        Widget init = new Widget();
        init.setX(10.0);
        init.setY(10.0);
        init.setHeight(10.0);
        init.setWidth(10.0);
        init.setZIndex(10L);

        widgetService.add(init);

        Widget ex = new Widget();
        ex.setId(init.getId());
        ex.setX(100.0);
        ex.setY(100.0);
        ex.setHeight(100.0);
        ex.setWidth(100.0);
        ex.setZIndex(100L);

        Widget actual = widgetService.update(ex);

        assertEquals(ex, actual);
    }

    @Test
    public void deleteWidget() {
        Widget ex = new Widget();
        ex.setX(100.0);
        ex.setY(100.0);
        ex.setHeight(100.0);
        ex.setWidth(100.0);
        ex.setZIndex(100L);

        widgetService.add(ex);

        widgetService.delete(ex.getId());

        assertEquals(widgetService.getAll().size(), 0);
    }

    @Test
    public void getWidgetByRange() {
        Widget first = new Widget();
        first.setX(50.0);
        first.setY(50.0);
        first.setHeight(100.0);
        first.setWidth(100.0);
        first.setZIndex(100L);

        widgetService.add(first);

        Widget second = new Widget();
        second.setX(50.0);
        second.setY(100.0);
        second.setHeight(100.0);
        second.setWidth(100.0);
        second.setZIndex(100L);

        widgetService.add(second);

        Widget third = new Widget();
        third.setX(100.0);
        third.setY(100.0);
        third.setHeight(100.0);
        third.setWidth(100.0);
        third.setZIndex(100L);

        widgetService.add(third);

        RangeParams rp = new RangeParams(0.0, 0.0, 100.0, 150.0);

        assertEquals(widgetService.getByRange(rp).size(), 2);
    }

    @Test
    public void getPages() {
        Widget ex = new Widget();
        ex.setX(100.0);
        ex.setY(100.0);
        ex.setHeight(100.0);
        ex.setWidth(100.0);
        ex.setZIndex(100L);

        for (int i = 0; i < 999; i++) {
            widgetService.add(ex);
        }

        PageParams pageParams = new PageParams(1L, 100L);

        assertEquals(100, (long) widgetService.getPage(pageParams).getElems().size());

        pageParams = new PageParams(2L, 999L);

        assertEquals(499, (long) widgetService.getPage(pageParams).getElems().size());

        pageParams = new PageParams(1L, 999L);

        assertEquals(500, (long) widgetService.getPage(pageParams).getElems().size());

        pageParams = new PageParams(null, null);

        assertEquals( 10, (long) widgetService.getPage(pageParams).getElems().size());

    }


}