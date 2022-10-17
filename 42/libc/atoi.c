#include "libft.h"

int ft_atoi(const char *str)
{
	int	signe;
	int	result;

	signe = 1;
	result = 0;
	if  (*str == '+')
        str++;
	else if (*str == '-')
    {
		signe = -1;
		str++;
	}
	while (*str == ' ' || *str == '\f' || *str == '\n' || *str == '\r' || *str == '\t' || *str == '\v')
		str++;
	while (*str)
	{
		if (*str >= '0' && *str <= '9')
			result = (result * 10) + (*str - '0');
		else
			break ;
		str++;
	}
	return (result  * signe);
}