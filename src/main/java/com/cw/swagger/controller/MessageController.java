package com.cw.swagger.controller;

import com.cw.swagger.config.BaseResult;
import com.cw.swagger.entity.Message;
import com.cw.swagger.service.MessageService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "消息", description = "消息操作 API", position = 100, protocols = "http")
@RestController
@RequestMapping("/")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@ApiOperation(
			value = "消息列表",
			notes = "完整的消息内容列表",
			produces="application/json, application/xml",
			consumes="application/json, application/xml",
			response = List.class)
	@GetMapping(value = "messages")
	public List<Message> list() {
		List<Message> messages = this.messageService.findAll();
		return messages;
	}

	@ApiOperation(
			value = "添加消息",
			notes = "根据参数创建消息"
	)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "消息 ID", required = true, dataType = "Long", paramType = "query"),
			@ApiImplicitParam(name = "text", value = "正文", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "summary", value = "摘要", required = false, dataType = "String", paramType = "query"),
	})
	@PostMapping(value = "message")
	public Message create(Message message) {
		System.out.println("message===="+message.toString());
		message = this.messageService.save(message);
		return message;
	}

	@ApiOperation(
			value = "修改消息",
			notes = "根据参数修改消息"
	)
	@PutMapping(value = "message")
	@ApiResponses({
			@ApiResponse(code = 100, message = "请求参数有误"),
			@ApiResponse(code = 101, message = "未授权"),
			@ApiResponse(code = 103, message = "禁止访问"),
			@ApiResponse(code = 104, message = "请求路径不存在"),
			@ApiResponse(code = 200, message = "服务器内部错误")
	})
	public Message modify(Message message) {
		Message messageResult=this.messageService.update(message);
		return messageResult;
	}

	@ApiOperation(
			value = "消息补丁"
	)
	@PatchMapping(value="/message/text")
	public BaseResult<Message> patch(Message message) {
		Message messageResult=this.messageService.updateText(message);
		return BaseResult.successWithData(messageResult);
	}

	@ApiOperation(
			value = "消息详情",
			notes = "根据ID查看消息详情"
	)
	@GetMapping(value = "message/{id}")
	public Message get(@PathVariable Long id) {
		Message message = this.messageService.findMessage(id);
		return message;
	}

	@ApiOperation(
			value = "删除消息",
			notes = "根据ID删除消息"
	)
	@DeleteMapping(value = "message/{id}")
	public void delete(@PathVariable("id") Long id) {
		this.messageService.deleteMessage(id);
	}

}