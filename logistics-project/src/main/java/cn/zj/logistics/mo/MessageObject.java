package cn.zj.logistics.mo;

public class MessageObject {

	private Integer num;
	private String msg;

	public MessageObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageObject(Integer num, String msg) {
		super();
		this.num = num;
		this.msg = msg;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "MessageObject [num=" + num + ", msg=" + msg + "]";
	}

}
