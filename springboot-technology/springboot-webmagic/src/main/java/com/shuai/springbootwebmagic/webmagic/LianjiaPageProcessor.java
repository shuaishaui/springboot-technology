package com.shuai.springbootwebmagic.webmagic;

import java.util.List;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class LianjiaPageProcessor implements PageProcessor {
  private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

  @Override
  public void process(Page page) {
    Html html = page.getHtml();

    // 房源详情连接
    List<String> list = html.css(".content__list--item--title a").links().all();
    page.addTargetRequests(list);

    String title = html.xpath("//div[@class='content clear w1150']/p/text()").toString();
    page.putField("title", title);

    page.putField("rent", html.xpath("//p[@class='content__aside--title']/span/text()").toString());
    page.putField("type", html.xpath("//p[@class='content__article__table']/allText()").toString());
    page.putField("info", html.xpath("//div[@class='content__article__info']/allText()").toString());
    page.putField("img", html.xpath("//div[@class='content__article__slide__item']/img").toString());

    // 获取，为空，代表现在的是列表页面，就skip跳过，不去获取数据
    if(page.getResultItems().get("title") == null){
      page.setSkip(true);

      // 分页连接，前面的参考具体网页
      for (int i = 1; i <= 100; i++) {
        page.addTargetRequest("https://sh.lianjia.com/zufang/pg"+i);
      }
    }

  }

  @Override
  public Site getSite() {
    return site;
  }

  public static void main(String[] args) {
    Spider.create(new LianjiaPageProcessor())
        .addUrl("https://sh.lianjia.com/zufang/")
        .addPipeline(new MyPipeline())
        .thread(1).run();
  }
}
