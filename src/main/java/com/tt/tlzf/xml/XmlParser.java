package com.tt.tlzf.xml;

import com.tt.tlzf.xstruct.common.AipgReq;
import com.tt.tlzf.xstruct.common.AipgRsp;
import com.tt.tlzf.xstruct.common.InfoReq;
import com.tt.tlzf.xstruct.quickpay.*;
import com.tt.tlzf.xstruct.trans.*;
import com.tt.tlzf.xstruct.trans.breq.Trans_Detail;
import com.tt.tlzf.xstruct.trans.brsp.Body;
import com.tt.tlzf.xstruct.trans.brsp.Ret_Detail;
import com.tt.tlzf.xstruct.trans.qry.QTDetail;
import com.tt.tlzf.xstruct.trans.qry.QTransRsp;
import com.tt.tlzf.xstruct.trans.qry.TransQueryReq;
import com.tt.tlzf.xstruct.ver.*;
import com.tt.tlzf.xstruct.ver.idv.IdVer;
import com.tt.tlzf.xstruct.ver.idv.VALIDRETDTL;
import com.tt.tlzf.xstruct.ver.idv.VerQry;
import com.thoughtworks.xstream.XStream;

/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2018年5月23日
 **/
public class XmlParser {
	public static final String HEAD = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
	private static final XStream xsreq = initXStream(new XStreamEx(), true);
	private static final XStream xsrsp = initXStream(new XStreamEx(), false);

	public static AipgReq parseReq(String xml) {
		return (AipgReq) xsreq.fromXML(xml);
	}

	public static AipgRsp parseRsp(String xml) {
		return (AipgRsp) xsrsp.fromXML(xml);
	}

	public static String toXml(Object o) {
		return (o instanceof AipgReq) ?  XmlParser.toXml(xsreq, o) : XmlParser.toXml(xsrsp, o);

	}

	public static String toXml(XStream xs, Object o) {
		String xml;
		xml = xs.toXML(o);
		xml = xml.replaceAll("__", "_");
		xml = HEAD + xml;
		return xml;
	}

	public static AipgReq xmlReq(String xmlMsg) {
		AipgReq req = (AipgReq) xsreq.fromXML(xmlMsg);
		return req;
	}

	public static AipgRsp xmlRsp(String xmlMsg) {
		AipgRsp rsp = (AipgRsp) xsrsp.fromXML(xmlMsg);
		return rsp;
	}

	public static String reqXml(AipgReq req) {
		String xml = HEAD + xsreq.toXML(req);
		xml = xml.replace("__", "_");
		return xml;
	}

	public static String rspXml(AipgRsp rsp) {
		String xml = HEAD + xsrsp.toXML(rsp);
		xml = xml.replace("__", "_");
		return xml;
	}

	public static XStream initXStream(XStream xs, boolean isreq) {
		if (isreq) {
			xs.alias("AIPG", AipgReq.class);
			xs.alias("BODY", com.tt.tlzf.xstruct.trans.breq.Body.class);
			xs.alias("TRANS_DETAIL", Trans_Detail.class);
			xs.aliasField("TRANS_DETAILS", com.tt.tlzf.xstruct.trans.breq.Body.class,
					"details");
		} else {
			xs.alias("AIPG", AipgRsp.class);
			xs.alias("BODY", Body.class);
		}
		xs.alias("INFO", InfoReq.class);
		xs.addImplicitCollection(AipgReq.class, "trxData");
		xs.addImplicitCollection(AipgRsp.class, "trxData");
		xs.alias("QTRANSREQ", TransQueryReq.class);
		xs.alias("QTRANSRSP", QTransRsp.class);
		xs.alias("QTDETAIL", QTDetail.class);
		xs.alias("TRANS", TransExt.class);
		xs.alias("LEDGERS", Ledgers.class);
		xs.addImplicitCollection(Ledgers.class, "list");
		xs.alias("LEDGERDTL", LedgerDtl.class);
		xs.alias("TRANSRET", TransRet.class);

		xs.alias("VALIDR", ValidR.class);
		xs.alias("VALIDTR", ValidTR.class);
		xs.alias("VALIDRET", ValidRet.class);

		xs.alias("IDVER", IdVer.class);
		xs.alias("VERQRY", VerQry.class);


		xs.alias("VALIDBREQ", ValidBReq.class);
		xs.alias("VALBSUM", ValbSum.class);
		xs.alias("VALIDBD", ValidBD.class);
		xs.alias("VBDETAIL", VbDetail.class);
		xs.addImplicitCollection(ValidBD.class, "details");

		xs.addImplicitCollection(QTransRsp.class, "details");

		xs.alias("CASHREQ", CashReq.class);
		xs.alias("CASHREP", CashRep.class);

		xs.aliasField("RET_DETAILS", Body.class, "details");
		xs.alias("RET_DETAIL", Ret_Detail.class);

		xs.alias("REFUND", Refund.class);

		xs.alias("CHARGEREQ", ChargeReq.class);
		xs.alias("VALIDRETDTL", VALIDRETDTL.class);


		xs.alias("RNP", RNP.class);
		xs.alias("RNPA", RNPA.class);
		xs.alias("RNPARET", RNPARET.class);
		xs.alias("RNPR", RNPR.class);
		xs.alias("RNPRRET", RNPRRET.class);
		xs.alias("RNPC", RNPC.class);
		xs.alias("RNPCRET", RNPCRET.class);

		xs.alias("FAGRA", FAGRA.class);
		xs.alias("FAGRARET", FAGRARET.class);
		xs.alias("FAGRC", FAGRCEXT.class);
		xs.alias("FAGRCRET", FAGRCRET.class);
		xs.alias("FAGRCNL", FAGRCNL.class);
		xs.alias("FAGRCNLRET", FAGRCNLRET.class);
		xs.alias("FASTTRX", FASTTRX.class);
		xs.alias("FASTTRXRET", FASTTRXRET.class);
		xs.alias("QAGRINFO", QAGRINFO.class);
		return xs;
	}


}
