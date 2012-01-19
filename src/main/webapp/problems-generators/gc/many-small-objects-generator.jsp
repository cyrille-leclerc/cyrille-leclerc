
<%
    for (int i = 0; i < 100000; i++) {
        char[] chars = new char[1024];
        
        for (int j = 0; j < chars.length; j++) {
            chars[j] = 'a';
            if ((j % 100) == 0) {
                if(chars[j] != 'a') {
                    // fake logic to useless code removal by the compiler
                    out.print(j);
                }
            }
        }
    }
%>