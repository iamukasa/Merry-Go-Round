package com.example.makeshift.merrygoround;

/**
 * Created by irving on 4/22/15.
 */
public class ContributionsItems {
    private String number;
    private String contribution;
    private String dates;

    public ContributionsItems(){
        number="" ;
        contribution="";
        dates="";

    }

    public ContributionsItems(String Number, String Contribution,String Dates){
       this.number=Number;
        this.contribution=Contribution;
        this.dates=Dates;
            }
     public String getNumber(){
         return number;
      }
      public  void setNumber(String Number ){
     Number=number;
      }
    public String getContribution(){
        return contribution;
    }
    public  void setContribution(String Contribution){
        Contribution=contribution;
    }
    public String getDates(){
        return dates;
    }
    public  void setDates(String Dates){
        Dates=dates;
    }

    }
