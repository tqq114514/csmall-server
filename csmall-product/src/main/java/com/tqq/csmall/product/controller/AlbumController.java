package com.tqq.csmall.product.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tqq.csmall.product.pojo.param.AlbumAddNewParam;
import com.tqq.csmall.product.pojo.param.AlbumUpdateInfoParam;
import com.tqq.csmall.product.pojo.vo.AlbumListItemsVO;
import com.tqq.csmall.product.pojo.vo.AlbumStandardVO;
import com.tqq.csmall.product.pojo.vo.PageData;
import com.tqq.csmall.product.services.IAlbumService;
import com.tqq.csmall.product.web.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/album")
@Api(tags = "02相册管理模块")
@Slf4j
@Validated
public class AlbumController {

    @Autowired
    private IAlbumService ialbumService;


    /*@RequestMapping(value = "/add-new",method = {RequestMethod.POST,RequestMethod.GET})*/
    /*@PostMapping(value = "/add-new")*/
    /*@RequestMapping(value = {"/add-new","/add"},method = RequestMethod.POST)*/
    @RequestMapping(value = "/add-new",method = RequestMethod.POST)
    @ApiOperation(value = "添加相册")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid @RequestBody AlbumAddNewParam albumAddNewParam){
        log.debug("开始处理【添加相册】的请求，参数：{}", albumAddNewParam);
        ialbumService.addNew(albumAddNewParam);
        return JsonResult.ok();
    }

    /*该请求没有封装参数，需要使用ApiImplicitParam添加在方法上，必须配置name属性与
     * 方法属性值相等*/
    /*http://localhost/album/delete?albumId=1&userId=1*/


    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "albumId",value = "相册Id",required = true,dataType = "long"),
            @ApiImplicitParam(name = "userId",value = "用户Id",required = true,dataType = "long")
    })
    public String delete(@Range(min = 1,message = "ID值不合法") @RequestParam Long albumId, Long userId){
        throw new RuntimeException("to do");
    }*/
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation("根据ID删除相册")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id",value = "相册Id",required = true,dataType = "long")
    public JsonResult delete(@Range(min = 1,message = "ID值不合法") @RequestParam Long id){
        log.debug("开始处理【根据ID删除相册】的请求,参数：{}",id);
        ialbumService.delete(id);
        return JsonResult.ok();
    }

    @PostMapping("/update")
    @ApiOperation("修改相册")
    @ApiOperationSupport(order =300)
    @ApiImplicitParam(name = "id",value = "相册Id",required = true,dataType = "long")
    public JsonResult updateInfoById(@Range(min = 1,message = "ID值不合法") @RequestParam Long id,
                                     @Valid  AlbumUpdateInfoParam albumUpdateInfoParam) {
        log.debug("开始处理【修改相册详情】的请求，ID：{}，新数据：{}", id, albumUpdateInfoParam);
        ialbumService.updateInfoById(id, albumUpdateInfoParam);
        return JsonResult.ok();
    }

    @GetMapping("/standard")
    @ApiOperation("查询单个相册以供修改")
    @ApiOperationSupport(order =400)
    @ApiImplicitParam(name = "id",value = "相册Id",required = true,dataType = "long")
    public JsonResult getStandardById(@Range(min = 1,message = "ID值不合法") @RequestParam Long id) {
        log.debug("开始处理【根据ID查询相册详情】的请求，参数：{}", id);
        AlbumStandardVO result = ialbumService.getStandardById(id);
        return JsonResult.ok(result);
    }


    @GetMapping("/list")
    @ApiOperation("查询相册列表")
    @ApiOperationSupport(order =410)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页码",paramType = "query")
    })
    public JsonResult list(@Range(min = 1,message = "查询相册列表失败，提供的页码值有误！") Integer pageNum) {
        log.debug("开始处理【查询相册列表】的请求，页码：{}", pageNum);
        if (pageNum == null || pageNum < 1 ){
            pageNum = 1;
        }
        PageData<AlbumListItemsVO> pageData = ialbumService.list(pageNum);
        return JsonResult.ok(pageData);
    }
}

