package com.readboy.mentalcalculation.other;

/**
 * Created by Wyd on 2017/2/23.
 */

public class FAO {

    private static char[] symble_list = {'+','-','×','÷'};
    private node1 root;
    private int num_symble;
    public String pro="";
    public String ans1="";
    private double pl = 0.6 , pr = 0.6;
    private boolean interrupt;

    public String faogo()
    {
        String ans="";
        while(true)
        {
            interrupt = false;
            num_symble=0;
            int t = (int)(2+Math.random()*98);
            root=new node1( symble_list[(int)(Math.random()*4)], new fs((int)(1+(Math.random()*(t-1))) , t ));
            pl = 0.3 ; pr = 0.3;
            factor(root);

            if(interrupt)
                continue;

            if(num_symble>=2)
            {
                int j=0;
                ans=visit(root,'r',' ');
                for(int i=0;i<4;i++)
                    if(ans.indexOf(symble_list[i])!=-1)
                        j++;
                if(j>=2)
                    break;
            }
        }
        pro=ans;
        ans1=root.getele();
        System.out.println(ans+"="+root.getele());
        return tostr(ans+"="+root.getele());
    }

    private String visit(node1 top,char fsymb,char lr)
    {
        if(top.lc==top.rc)
            return top.getele();
        if((top.symble=='+' || top.symble=='-') && (fsymb=='×' || fsymb=='÷'))
            return "("+visit(top.lc,top.symble,'l')+top.symble+visit(top.rc,top.symble,'r')+")";
        if((fsymb=='÷') && (top.symble=='×' || top.symble=='÷') && (lr=='r'))
            return "("+visit(top.lc,top.symble,'l')+top.symble+visit(top.rc,top.symble,'r')+")";
        if((fsymb=='-') && (top.symble=='+' || top.symble=='-') && (lr=='r'))
            return "("+visit(top.lc,top.symble,'l')+top.symble+visit(top.rc,top.symble,'r')+")";

        return visit(top.lc,top.symble,'l')+top.symble+visit(top.rc,top.symble,'r');
    }

    private void factor(node1 top)
    {
        if(num_symble>=5)
            return;
        addnode(top);
        num_symble++;
        if(Math.random()<0.5)
        {
            if(Math.random()<pl)
            {
                factor(top.lc);
                pl/=2;
            }

            if(Math.random()<pr)
            {
                factor(top.rc);
                pr/=2;
            }

        }
        else
        {
            if(Math.random()<pr)
            {
                factor(top.rc);
                pr/=2;
            }

            if(Math.random()<pl)
            {
                factor(top.lc);
                pl/=2;
            }

        }
        return;
    }

    private void addnode(node1 top)
    {
        fs l=new fs(100,100),r=new fs(100,100);
        int i=0;
        switch(top.symble)
        {
            case '+' :
                while(l.fm>=100||l.fz>=100 || l.fm<=1|| l.fz<1)
                {
                    r=new fs((int)(1+Math.random()*99) , (int)(2+Math.random()*98));
                    l=fs.fracSub(top.ele.fz, top.ele.fm, r.fz, r.fm);
                    if(i++>1000)
                    {
                        interrupt=true;
                        break;
                    }
                }
                break;

            case '-' :
                while(l.fm>=100||l.fz>=100 || l.fm<=1|| l.fz<1)
                {
                    r=new fs((int)(1+Math.random()*99) , (int)(2+Math.random()*98));
                    l=fs.fracAdd(top.ele.fz, top.ele.fm, r.fz, r.fm);
                    if(i++>1000)
                    {
                        interrupt=true;
                        break;
                    }
                }
                break;

            case '×' :
                while(l.fm>=100||l.fz>=100 || l.fm<=1|| l.fz<1)
                {
                    r=new fs((int)(1+Math.random()*99) , (int)(2+Math.random()*98));
                    l=fs.fractDiv(top.ele.fz, top.ele.fm, r.fz, r.fm);
                    if(i++>1000)
                    {
                        interrupt=true;
                        break;
                    }
                }
                break;

            case '÷' :
                while(l.fm>=100||l.fz>=100 || l.fm<=1|| l.fz<1)
                {
                    r=new fs((int)(1+Math.random()*99) , (int)(2+Math.random()*98));
                    l=fs.fracMul(top.ele.fz, top.ele.fm, r.fz, r.fm);
                    if(i++>1000)
                    {
                        interrupt=true;
                        break;
                    }
                }
                break;
        }
        top.lc = new node1(symble_list[(int)(Math.random()*4)],l);
        top.rc = new node1(symble_list[(int)(Math.random()*4)],r);
    }

    public static String tostr(String str)
    {
        String outans = "",outpro="";
        char[] c = str.toCharArray();
        int p=0;
        while(p<c.length && c[p]!='=')
        {
            if(c[p]>='0'&&c[p]<='9')
            {
                boolean z = true;
                while( p<c.length && (('0'<=c[p]&&c[p]<='9') || c[p]=='/') )
                {
                    if(c[p]!='/')
                        outans+=c[p];
                    else
                    {
                        z=false;
                        outans+=';';
                    }
                    p++;
                }
                if(z)
                    outpro+='!';
                else
                    outpro+='?';
                outans+=';';
            }
            else
                outpro+=c[p++];
        }

        outpro+=c[p++];

        if(p>=c.length)
            return outpro+'@'+outans;
        int pt=p;
        boolean f=false;
        while(pt<c.length)
        {
            if(!(('0'<=c[pt]&&c[pt]<='9') || c[pt]=='/'))
                f=true;
            pt++;
        }
        if(c[--pt]=='/' || c[p]=='/')
            f=true;

        if(f)
        {
            while(p<c.length)
                outans+=c[p++];
            outans+=";";
            outpro+="!";
        }
        else
        {
            boolean z = true;
            while(p<c.length)
            {
                if(c[p]!='/')
                    outans+=c[p];
                else
                {
                    z=false;
                    outans+=';';
                }
                p++;
            }
            if(z)
                outpro+='!';
            else
                outpro+='?';
            outans+=';';
        }
        return outpro+'@'+outans;
    }
}

class node1 {
    node1 lc;
    node1 rc;
    char symble;
    fs ele;

    node1(char symble,fs ele)
    {
        this.symble=symble;
        this.ele=ele;
        lc=null;
        rc=null;
    }

    public String getele()
    {
        if(ele.fm==0)
            return "'error'";
        if(ele.fz==0)
            return "0";
        else
        {
            /**int gcd = fs.gcd(ele.fz,ele.fm);
             ele.fz = ele.fz / gcd;
             ele.fm = ele.fm / gcd;*/
            return ele.fz+"/"+ele.fm;
        }
    }
}

class fs {
    int fz,fm;
    fs(int fz,int fm)
    {
        this.fz=fz;
        this.fm=fm;
    }

    static fs fracAdd(int first_numerator,int first_denominator,int second_numrator,int second_denominator){

        int denominator;
        int numerator;

        if(first_denominator==second_denominator)
        {
            denominator=first_denominator;
            numerator=first_numerator+second_numrator;
        }
        else
        {
            denominator=first_denominator*second_denominator;
            numerator=first_numerator*second_denominator+first_denominator*second_numrator;
        }
        int gcd = gcd(numerator,denominator);
        denominator = denominator / gcd;
        numerator = numerator / gcd;

        return new fs(numerator,denominator);

    }
    static fs fracSub(int first_numerator,int first_denominator,int second_numrator,int second_denominator){

        int denominator;
        int numerator;

        if(first_denominator==second_denominator)
        {
            denominator=first_denominator;
            numerator=first_numerator-second_numrator;
        }
        else
        {
            denominator=first_denominator*second_denominator;
            numerator=first_numerator*second_denominator-first_denominator*second_numrator;
        }
        int gcd = gcd(numerator,denominator);
        denominator = denominator / gcd;
        numerator = numerator / gcd;

        return new fs(numerator,denominator);

    }
    static fs fracMul(int first_numerator,int first_denominator,int second_numerator,int second_denominator){

        int denominator;
        int numerator;

        denominator=first_denominator*second_denominator;
        numerator=first_numerator*second_numerator;

        int gcd = gcd(numerator,denominator);
        denominator = denominator / gcd;
        numerator = numerator / gcd;

        return new fs(numerator,denominator);

    }
    static fs fractDiv(int first_numerator,int first_denominator,int second_numerator,int second_denominator){

        int denominator;
        int numerator;

        numerator = first_numerator*second_denominator;
        denominator = first_denominator*second_numerator;

        int gcd = gcd(numerator,denominator);
        denominator = denominator / gcd;
        numerator = numerator / gcd;

        return new fs(numerator,denominator);

    }
    static int gcd(int x,int y){
        int r;
        while( y!= 0)
        {
            r = x%y;
            x = y;
            y = r;
        }
        return x;
    }
}
//    private static char[] symble_list = {'+','-','×','÷'};
//    private node1 root;
//    private int num_symble;
//    private double pl = 0.6 , pr = 0.6;
//    public String pro="";
//    public String ans1="";
//
//    public String faogo()
//    {
//        String ans="";
//        while(true)
//        {
//            num_symble=0;
//            int t = (int)(1+Math.random()*99);
//            root=new node1( symble_list[(int)(Math.random()*4)], new fs((int)(1+Math.random()*(t-1)) , t ));
//            pl = 0.3 ; pr = 0.3;
//            factor(root);
//            if(num_symble>=2)
//            {
//                int j=0;
//                ans=visit(root,'r',' ');
//                for(int i=0;i<4;i++)
//                    if(ans.indexOf(symble_list[i])!=-1)
//                        j++;
//                if(j>=2)
//                    break;
//            }
//        }
////        System.out.println(ans+"="+root.getele());
//        pro=ans;
//        ans1=root.getele();
//        return tostr(ans+"="+root.getele());
//    }
//
//    private String visit(node1 top,char fsymb,char lr)
//    {
//        if(top.lc==top.rc)
//            return top.getele();
//        if((top.symble=='+' || top.symble=='-') && (fsymb=='×' || fsymb=='÷'))
//            return "("+visit(top.lc,top.symble,'l')+top.symble+visit(top.rc,top.symble,'r')+")";
//        if((fsymb=='÷') && (top.symble=='×' || top.symble=='÷') && (lr=='r'))
//            return "("+visit(top.lc,top.symble,'l')+top.symble+visit(top.rc,top.symble,'r')+")";
//        if((fsymb=='-') && (top.symble=='+' || top.symble=='-') && (lr=='r'))
//            return "("+visit(top.lc,top.symble,'l')+top.symble+visit(top.rc,top.symble,'r')+")";
//
//        return visit(top.lc,top.symble,'l')+top.symble+visit(top.rc,top.symble,'r');
//    }
//
//    private void factor(node1 top)
//    {
//        if(num_symble>=5)
//            return;
//        addnode(top);
//        num_symble++;
//        if(Math.random()<0.5)
//        {
//            if(Math.random()<pl)
//            {
//                factor(top.lc);
//                pl/=2;
//            }
//
//            if(Math.random()<pr)
//            {
//                factor(top.rc);
//                pr/=2;
//            }
//
//        }
//        else
//        {
//            if(Math.random()<pr)
//            {
//                factor(top.rc);
//                pr/=2;
//            }
//
//            if(Math.random()<pl)
//            {
//                factor(top.lc);
//                pl/=2;
//            }
//
//        }
//        return;
//    }
//
//    private void addnode(node1 top)
//    {
//        fs l=new fs(1,1),r=new fs(1,1);
//        switch(top.symble)
//        {
//            case '+' :
//                l.fm=100;
//                l.fz=100;
//                while(l.fm>=100||l.fz>=100 || l.fm<=0|| l.fz<0)
//                {
//                    r=new fs((int)(Math.random()*100) , (int)(1+Math.random()*99));
//                    l=fs.fracSub(top.ele.fz, top.ele.fm, r.fz, r.fm);
//                }
//                break;
//
//            case '-' :
//                l.fm=100;
//                l.fz=100;
//                while(l.fm>=100||l.fz>=100 || l.fm<=0|| l.fz<0)
//                {
//                    r=new fs((int)(Math.random()*100) , (int)(1+Math.random()*99));
//                    l=fs.fracAdd(top.ele.fz, top.ele.fm, r.fz, r.fm);
//                }
//                break;
//
//            case '×' :
//                l.fm=100;
//                l.fz=100;
//                while(l.fm>=100||l.fz>=100 || l.fm<=0|| l.fz<0)
//                {
//                    r=new fs((int)(Math.random()*100) , (int)(1+Math.random()*99));
//                    l=fs.fractDiv(top.ele.fz, top.ele.fm, r.fz, r.fm);
//                }
//                break;
//
//            case '÷' :
//                l.fm=100;
//                l.fz=100;
//                while(l.fm>=100||l.fz>=100 || l.fm<=0|| l.fz<0)
//                {
//                    r=new fs((int)(1+Math.random()*99) , (int)(1+Math.random()*99));
//                    l=fs.fracMul(top.ele.fz, top.ele.fm, r.fz, r.fm);
//                }
//                break;
//        }
//        top.lc = new node1(symble_list[(int)(Math.random()*4)],l);
//        top.rc = new node1(symble_list[(int)(Math.random()*4)],r);
//    }
//
//    public static String tostr(String str)
//    {
//        String outans = "",outpro="";
//        char[] c = str.toCharArray();
//        String s = "";
//        for (int l = 0;l<c.length;l++)
//            s+=c[l];
//          Log.e("66666666666666", s+"");
//        int p=0;
//        while(p<c.length)
//        {
//            if((c[p]!='+')&&(c[p]!='=')&&(c[p]!='-')&&(c[p]!='×')&&(c[p]!='÷')&&(c[p]!='(')&&(c[p]!=')'))
//            {
//                    outpro+='?';
////                && ((('0'<=c[p]&&c[p]<='9')||(c[p]==','||c[p]=='/')) || c[p]=='$')
//                    while((p<c.length)&&((c[p]!='+')&&(c[p]!='-')&&(c[p]!='×')&&(c[p]!='÷')&&(c[p]!='=')&&(c[p]!='(')&&(c[p]!=')')))
//                    {
//                        if(c[p]!='除')
//                            outans+=c[p];
//                        else
//                            outans+=';';
//                        p++;
//                    }
//                    outans+=';';
//
//            }
//            else
//                outpro+=c[p++];
//        }
//        Log.e("7777777777777777", outpro+'@'+outans+"");
//        return outpro+'@'+outans;
//    }
//}
//
//class node1 {
//    node1 lc;
//    node1 rc;
//    char symble;
//    fs ele;
//
//    node1(char symble,fs ele)
//    {
//        this.symble=symble;
//        this.ele=ele;
//        lc=null;
//        rc=null;
//    }
//
//    public String getele()
//    {
//        if(ele.fm==0)
//            return "'error'";
//        if(ele.fz==0)
//            return "0";
//        else
//        {
//            int gcd = fs.gcd(ele.fz,ele.fm);
//            ele.fz = ele.fz / gcd;
//            ele.fm = ele.fm / gcd;
//            return ele.fz+"除"+ele.fm;
//        }
//    }
//}
//
//class fs {
//    int fz,fm;
//    fs(int fz,int fm)
//    {
//        this.fz=fz;
//        this.fm=fm;
//    }
//
//    static fs fracAdd(int first_numerator,int first_denominator,int second_numrator,int second_denominator){
//
//        int denominator;
//        int numerator;
//
//        if(first_denominator==second_denominator)
//        {
//            denominator=first_denominator;
//            numerator=first_numerator+second_numrator;
//        }
//        else
//        {
//            denominator=first_denominator*second_denominator;
//            numerator=first_numerator*second_denominator+first_denominator*second_numrator;
//        }
//        int gcd = gcd(numerator,denominator);
//        denominator = denominator / gcd;
//        numerator = numerator / gcd;
//
//        return new fs(numerator,denominator);
//
//    }
//    static fs fracSub(int first_numerator,int first_denominator,int second_numrator,int second_denominator){
//
//        int denominator;
//        int numerator;
//
//        if(first_denominator==second_denominator)
//        {
//            denominator=first_denominator;
//            numerator=first_numerator-second_numrator;
//        }
//        else
//        {
//            denominator=first_denominator*second_denominator;
//            numerator=first_numerator*second_denominator-first_denominator*second_numrator;
//        }
//        int gcd = gcd(numerator,denominator);
//        denominator = denominator / gcd;
//        numerator = numerator / gcd;
//
//        return new fs(numerator,denominator);
//
//    }
//    static fs fracMul(int first_numerator,int first_denominator,int second_numerator,int second_denominator){
//
//        int denominator;
//        int numerator;
//
//        denominator=first_denominator*second_denominator;
//        numerator=first_numerator*second_numerator;
//
//        int gcd = gcd(numerator,denominator);
//        denominator = denominator / gcd;
//        numerator = numerator / gcd;
//
//        return new fs(numerator,denominator);
//
//    }
//    static fs fractDiv(int first_numerator,int first_denominator,int second_numerator,int second_denominator){
//
//        int denominator;
//        int numerator;
//
//        numerator = first_numerator*second_denominator;
//        denominator = first_denominator*second_numerator;
//
//        int gcd = gcd(numerator,denominator);
//        denominator = denominator / gcd;
//        numerator = numerator / gcd;
//
//        return new fs(numerator,denominator);
//
//    }
//    static int gcd(int x,int y){
//        int r;
//        while( y!= 0)
//        {
//            r = x%y;
//            x = y;
//            y = r;
//        }
//        return x;
//    }

