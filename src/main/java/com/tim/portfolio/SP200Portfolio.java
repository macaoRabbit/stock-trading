package com.tim.portfolio;

import com.tim.experiment.PortfolioExperiment;
import com.tim.utility.FullExperiment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SP200Portfolio {
    static String symbols = "SMCI,NVDA,CEG,TSLA,BLDR,LLY,CARR,KLAC,MPWR,PWR,ENPH,AVGO,ANET,FICO,AXON,URI,MRNA,DECK,AMAT,KKR,FTNT,AMD,LRCX,PANW,AAPL,SNPS,ODFL,MCK,CDNS,HWM,DHI,STLD,FCX,ON,PHM,REGN,TRGP,ETN,CMG,MPC,FSLR,TT,JBL,GWW,PH,CRWD,COST,IRM,VST,AJG,CTAS,LEN,GE,MU,BX,BRO,MSFT,HUBB,IT,GOOG,GOOGL,ORLY,DVA,TER,GEHC,NOW,PGR,HCA,XLK,ACGL,AMP,AZO,CPRT,META,APH,COR,ISRG,NUE,CAT,NXPI,TDG,VRTX,IR,ORCL,TYL,GS,NVR,QQQ,MSI,TMUS,INTU,TSCO,ABBV,WST,LOW,CAH,MLM,QCOM,MOH,MCO,NRG,MSCI,DE,MS,RSG,HES,PNR,UNH,OTIS,GNRC,SHW,LIN,MMC,GDDY,KR,WAB,STX,HLT,CBRE,EFX,NDAQ,BRK-B,RJF,CDW,DHR,SPGI,EQT,NTAP,CI,GRMN,FAST,NWS,PKG,MRO,TMO,IVW,TJX,NWSA,ADI,AXP,MAS,BKNG,WMT,ADBE,HIG,PCAR,PTC,A,JPM,WRB,BLK,DXCM,CTVA,AVY,VLO,JBHT,MCHP,ROL,IVV,BSX,AME,CBOE,RMD,LDOS,AFL,AMZN,ELV,FANG,COP,FI,DOV,NFLX,VMC,CB,POOL,VRSK,ICE,J,MA,NDSN,TXN,UHS,AMGN,CMI,IDXX,WM,BBWI,JCI,AOS,ACN,ALL,AON,CRM,DFS,HD,HPQ,MTD,TXT,AIZ,FDX";
    static FullExperiment experiment = FullExperiment.Pair;
    static String date = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    static String resultFile = "sp200_" + experiment.toString() + "_" + date ;
    static Integer resultLimit = 10000;
    static String subDir = "sp200";

    public static void main(String[] args) {
        PortfolioExperiment.setSymbolsFileName(symbols, subDir, resultFile, resultLimit);
        PortfolioExperiment.setExperiment(experiment);
        PortfolioExperiment.run();
    }
}

