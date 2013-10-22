<%  
  session.removeAttribute("id");
  session.removeAttribute("name");
  session.removeAttribute("email");
  session.removeAttribute("status");
  response.sendRedirect("home?bye");
%>
