extern LArc RiflemanTBL[];
class CRifleman : public LFsaAppl
{
public:
     int GetNumber();
     void SetNumber(int n);
     void SetLink(CRifleman *pFsaLeft,
         CRifleman
*pFsaRigtht);
     CRifleman *pFsaRightMan;
     CRifleman *pFsaLeftMan;
     CRifleman();
     CRifleman(int n, CWnd* pW, LArc
         *pTBL=RiflemanTBL);
     virtual ~CRifleman();
     bool operator==(const CRifleman
         &var) const;
     bool operator<(const CRifleman
         &var) const;
     bool operator!=(const CRifleman
         &var) const;
     bool operator>(const CRifleman
         &var) const;
protected:
  CWnd*   pParentWnd;
  CFireApp *pApp; //  указатель на объект
             //  основного класса программы
  int x1();   //  Is fire?
  int x2();   //  Is ready?
  int x3();   //  Number is equal to zero? Shot!
  int x4();   //
  void y1();  //  To place number.
  void y2();  //  To reduce number by unit.
  void y3();  //  Gunshot
  void y4();  //
  void y5();  //
     int     nNumber;
     int     nSaveNumber;
     int nLengthQueue;   //  Length of queue.
     int nCurrentQueue;  //
};
typedef vector<CRifleman*>
TIArrayRifleman;
typedef vector<CRifleman*>:
:iterator TIIteratorRifleman;
extern LArc RiflemanTBL[];
CRifleman::CRifleman():LFsaAppl() { }
CRifleman::CRifleman(int n, CWnd* pW,
LArc* pTBL):
LFsaAppl(pTBL)
{
     pParentWnd = pW;
     pFsaRightMan = NULL;
     pFsaLeftMan = NULL;
     nNumber = n;
     nLengthQueue = 5;
     nCurrentQueue = nLengthQueue;
     if (pParentWnd)
     {
           pApp = (CFireApp*)AfxGetApp();
           FLoad(pApp->pNetFsa,1);
     }
}
bool CRifleman::operator==(const CRifleman
&var) const
{
     if (nNumber==var.nNumber) return true;
     else return false;
}
void CRifleman::SetLink(CRifleman
* pFsaLeft, CRifleman *
pFsaRigtht)
{
     pFsaRightMan = pFsaRigtht;
     pFsaLeftMan = pFsaLeft;
}
LArc RiflemanTBL[] = {
 LArc("Сон",     "Огонь",     "x1",  "y1"),
 LArc("Огонь",   "Готов",     "x2",  "y2"),
 LArc("Готов",   "Готов",     "x3",  "y2"),
 LArc("Готов",   "Выстрел",   "^x3", "y3y4"),
 LArc("Выстрел", "Выстрел",   "x4",  "y3y5"),
 LArc("Выстрел", "Сон",       "^x4", "-"),
 LArc()
  };
int CRifleman::x1()
{
     if (!pFsaLeftMan) return false;
     return string((pFsaLeftMan)-
         >FGetState()) == "Огонь";
}
int CRifleman::x2()
{
     if (!pFsaRightMan) return true;
     else return string((pFsaRightMan)-
         >FGetState()) ==
"Готов";
}
int CRifleman::x3() { return nNumber; }
int CRifleman::x4() { return nCurrentQueue; }
void CRifleman::y1()
{
     int n = pFsaLeftMan->GetNumber();
     SetNumber(n+1);
}
void CRifleman::y2() { nNumber-; }
void CRifleman::y3() { }
void CRifleman::y4()
{
        nCurrentQueue = nLengthQueue;
}
// формирование задержки между выстрелами
void CRifleman::y5()
{
        CFDelay *pCFDelay;
        pCFDelay = new CFDelay(200);
        pCFDelay->FCall(this);
        nCurrentQueue-;
}
