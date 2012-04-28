<?xml version="1.0" encoding="UTF-8" ?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<body>

	<div class="Panel Panel_MainMenu"></div>

	<div class="Gradient2"></div>

	<div class="Panel Panel_Content">

		<form name="loginForm" method="post" action="security_check">
			<table>
				<tr>
					<td align="right">Användarnamn</td>
					<td><input type="text" name="username" /></td>
				</tr>
				<tr>
					<td align="right">Lösenord</td>
					<td><input type="password" name="password" /></td>
				</tr>
				<tr>
					<td colspan="2" align="right"><input type="submit" value="Logga in" /> <input type="reset" value="Återställ" /></td>
				</tr>
			</table>
		</form>
	</div>

	<script type="text/javascript" language="JavaScript">
		document.forms['loginForm'].elements['username'].focus();
	</script>

</body>
</html>