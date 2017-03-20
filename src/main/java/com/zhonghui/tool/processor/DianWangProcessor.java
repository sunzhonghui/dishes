package com.zhonghui.tool.processor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhonghui.tool.bean.CatchAllUrl;
import com.zhonghui.tool.bean.DianwangFile;
import com.zhonghui.tool.service.CatchAllUrlService;
import com.zhonghui.tool.service.DianwangFileService;
import com.zhonghui.tool.util.DownloadFileUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
@Service
public class DianWangProcessor implements PageProcessor {
	@Autowired
	private CatchAllUrlService catchAllUrlService;
	@Autowired
	private DianwangFileService dianwangService;
	
	public static final String URL_LIST = ".*ecp.sgcc.com.cn/news_list.jsp\\?site=global&column_code=014002003.*";

    public static final String URL_POST = ".*ecp.sgcc.com.cn/project_list.jsp.*";

    private Site site = Site.me().setRetryTimes(3).setSleepTime(10000).setTimeOut(10000);

    public void process(Page page) {
    	
    	if(catchAllUrlService.getCatchAllUrlByUrl(page.getUrl().toString()).size()>0){
    		return;
    	}
    	CatchAllUrl catchAllUrl=new CatchAllUrl();
    	catchAllUrl.setUrl(page.getUrl().toString());
    	catchAllUrlService.inertCatchUrl(catchAllUrl);
        //ÁÐ±íÒ³
        if (page.getUrl().regex(URL_LIST).match()) {
        	String xpath="/html/body/div[@class='content']/div[@class='contentRight']/table//a//@onclick";//product_list
        		if(page.getUrl().toString().indexOf("014002003")>0){
        			xpath="/html/body/div[@class='content']/div[@class='contentRight']/div[@class='titleList']//a//@onclick";//new_list
        		}
        	List<String> urlist=page.getHtml().xpath(xpath).all();
        	for (String s : urlist) {
    				String[]  temp=s.split("'");
    				String ptype="project";
    				if(temp[0].startsWith("showNewsDetail")){
    					ptype="news";
    				}
     				String url="http://ecp.sgcc.com.cn/html/"+ptype+"/"+temp[1]+"/"+temp[3]+".html";
     				page.addTargetRequest(url);
    		}
            page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
            //ÎÄÕÂÒ³
        } else {
        	String links = page.getHtml().xpath("//table[@class='font02']/tbody/tr/td/a//@href").toString();
        	if(links==null)
        	links=page.getHtml().xpath("/html/body/div[@class='article']/p/a//@href").toString();
        	if(links==null)
        	links=page.getUrl().toString();
        	String title=page.getHtml().xpath("/html/head/title/text()").toString();
        	DianwangFile dianwang=new DianwangFile();
        	dianwang.setUrl(links);
        	dianwang.setTitle(title+page.getUrl().toString());
        	dianwangService.insertDiwangFile(dianwang);
        	String fileFlName="zbgg";
        	if(page.getUrl().toString().indexOf("014002003")>0){
        		fileFlName="zbcjjg";
        	}
        	DownloadFileUtil.downloadFile(links, "D:/java/jarbao/downloadfile/wzcg/"+fileFlName+"/"+title+links.substring(links.lastIndexOf(".")));
        	dianwangService.updateDiwangFileDownStatus(dianwang.getId());
        }
    }
    public Site getSite() {
        return site;
    }

    
}