@Grab(group='net.sf.opencsv', module='opencsv', version='2.3')
import au.com.bytecode.opencsv.*
import groovy.xml.MarkupBuilder

def cli = new CliBuilder(usage: 'groovy liner.groovy [options]')
cli.p(longOpt:'path-file', args:1, argName:'path-file',   'PATH Definitions File Path')
cli.l(longOpt:'list-file', args:1, argName:'list-file',   'Targets of take screenshots LIST File Path')
cli.w(longOpt:'width',     args:1, argName:'cell-width',  'WIDTH of a screenshot')
cli.h(longOpt:'height',    args:1, argName:'cell-height', 'HEIGHT of a screenshot')


def getIni(options) {
    def iniFile = (!options.p ? "./tssl.ini" : options.p)
    def config = new ConfigSlurper().parse(new File(iniFile).toURI().toURL())
    return config
}


def getCsv(options) {
    def csvFile = (!options.l ? "./tssl.csv" : options.l)
    def list = new CSVReader(new File(csvFile).newReader('UTF-8')).readAll()
    return list
}


def lineShots(ini, csv, argv) {
    listSize = csv.size()
    colSize = (listSize >= 10 ? 10 : listSize)
    columns = []
    for(i=0; i<colSize; i++){
        columns.add((i + 1).toString())
    }
    rowCount = (int)((listSize / 10) + 1)

    def w = (!argv.w ? 120 : argv.w)
    def h = (!argv.h ? 90 : argv.h)

    def sb = new StringBuilder()
    index=0
    tbRow = 0
    tbCol = 0

    sb.append("<table border='1'>"+"\n")
    for (row in csv) {
        if(row[0].startsWith("#url")){
            continue
        }
        if(tbCol==0){
            sb.append("<tr>"+"\n")
        }
        tbCol++

        sb.append("  <td>"+"\n")
        index = 0
        for (col in row) {
            if(index==0){
                altUrl = col
            }
            if(index==1){
                shortName = col
                imgPath = ini.apache.shotsdir+"/"+shortName+".png"
            }
            index++
        }
        sb.append("  "+shortName+"\n")
        sb.append("  "+"<img border='1' src='"+imgPath+"' width='"+w+"' height='"+h+"' alt='"+altUrl+"' />"+"\n")
        sb.append("  </td>"+"\n")

        if(tbCol==colSize){
            sb.append("</tr>"+"\n")
            tbCol = 0;
            tbRow++
        }
    }
    sb.append("</tr>"+"\n")
    sb.append("</table>"+"\n")

    sb.toString()
}


def outTable(tableStr) {
    tableStr.toString()
}


/*
 Script Execute Start
 */

argv = cli.parse(args)
ini = getIni(argv)
csv = getCsv(argv)
tableStr = lineShots(ini, csv, argv)

htmlPath = 'index.html'
if(ini.output.mode == "prod"){
    htmlPath = ini.output.htmldir+'/index.html'
}
def fileWriter = new FileWriter(htmlPath)
def builder = new MarkupBuilder(fileWriter)
builder.html {
    head {
        title 'Taken Screenshots'
    }
    body {
        h1 'HTML genarated at ' + (new Date().format("yyyy/MM/dd(E) HH:mm"))   //時刻

        div{
            mkp.yieldUnescaped(outTable(tableStr))
        }
    }
}

//cli.usage()
println "Check: "+ini.output.htmlurl