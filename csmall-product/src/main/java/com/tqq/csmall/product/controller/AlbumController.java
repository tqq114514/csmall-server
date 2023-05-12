package com.tqq.csmall.product.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tqq.csmall.product.pojo.param.AlbumAddNewParam;
import com.tqq.csmall.product.service.IAlbumService;
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
    private IAlbumService albumService;


    /*@RequestMapping(value = "/add-new",method = {RequestMethod.POST,RequestMethod.GET})*/
    /*@PostMapping(value = "/add-new")*/
    /*@RequestMapping(value = {"/add-new","/add"},method = RequestMethod.POST)*/
    @RequestMapping(value = "/add-new",method = RequestMethod.POST)
    @ApiOperation(value = "添加相册")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid AlbumAddNewParam albumAddNewParam){
        log.debug("开始处理【添加相册】的请求，参数：{}", albumAddNewParam);
        albumService.addNew(albumAddNewParam);
        return JsonResult.ok();
    }

    /*该请求没有封装参数，需要使用ApiImplicitParam添加在方法上，必须配置name属性与
     * 方法属性值相等*/
    /*http://localhost/album/delete?albumId=1&userId=1*/
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation("根据ID删除相册")
    @ApiOperationSupport(order = 200)

    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "albumId",value = "相册Id",required = true,dataType = "long"),
            @ApiImplicitParam(name = "userId",value = "用户Id",required = true,dataType = "long")
    })
    public String delete(@Range(min = 1,message = "ID值不合法") @RequestParam Long albumId, Long userId){
        throw new RuntimeException("to do");
    }*/

    @ApiImplicitParam(name = "albumId",value = "相册Id",required = true,dataType = "long")
    public String delete(@Range(min = 1,message = "ID值不合法") @RequestParam Long albumId){
        throw new RuntimeException("to do");
    }

    @PostMapping("/update")
    @ApiOperation("修改相册")
    @ApiOperationSupport(order =300)
    public String update() {
        throw new NullPointerException("修改出错了，导致了空指针异常！");
    }

    @PostMapping("/list")
    @ApiOperation("查询相册列表")
    @ApiOperationSupport(order =400)
    public String list() {
        throw new RuntimeException("查询出错了，导致了RuntimeException！");
    }
}

